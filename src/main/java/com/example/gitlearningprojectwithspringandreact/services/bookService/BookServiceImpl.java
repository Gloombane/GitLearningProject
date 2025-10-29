package com.example.gitlearningprojectwithspringandreact.services.bookService;

import com.example.gitlearningprojectwithspringandreact.entities.Book;
import com.example.gitlearningprojectwithspringandreact.entities.BookCategory;
import com.example.gitlearningprojectwithspringandreact.entities.BookCreateDTO;
import com.example.gitlearningprojectwithspringandreact.entities.Stock;
import com.example.gitlearningprojectwithspringandreact.repositories.BorrowedBookRepository;
import com.example.gitlearningprojectwithspringandreact.repositories.bookCategoryRepository.BookCategoryRepository;
import com.example.gitlearningprojectwithspringandreact.services.bookService.BookService;
import org.springframework.stereotype.Service;
import com.example.gitlearningprojectwithspringandreact.repositories.bookRepository.BookRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookCategoryRepository bookCategoryRepository;
    private final BorrowedBookRepository borrowedBookRepository;

    public BookServiceImpl(BookRepository bookRepository, BookCategoryRepository bookCategoryRepository, BorrowedBookRepository borrowedBookRepository) {
        this.bookRepository = bookRepository;
        this.bookCategoryRepository = bookCategoryRepository;
        this.borrowedBookRepository = borrowedBookRepository;
    }


    // Получить все книги
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAllWithCategory();
    }

    // Получить книгу по ID
    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книга не найдена с id: " + id));
    }

    @Override
    public void saveBook(BookCreateDTO request) {
        if (request.getReleaseDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Дата выпуска не может быть в будущем");
        }

        BookCategory category = bookCategoryRepository.findById(Math.toIntExact(request.getCategoryId()))
                .orElseThrow(() -> new RuntimeException("Категория не найдена"));

        Book book = Book.builder()
                .bookName(request.getBookName())
                .authorName(request.getAuthorName())
                .releaseDate(request.getReleaseDate())
                .bookImage(request.getBookImage())
                .bookCategory(category)
                .build();

        // Сохраняем книгу без стока
        Book savedBook = bookRepository.save(book);

        // Создаём сток для книги
        Stock stock = Stock.builder()
                .book(savedBook)
                .quantity(request.getQuantity()) // ✅ берём из DTO
                .build();

        savedBook.setStock(stock);

        bookRepository.save(savedBook); // повторно сохраняем с привязкой стока
    }



    @Override
    public void updateBook(Long id, BookCreateDTO updatedBook) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книга не найдена с id: " + id));

        if (updatedBook.getReleaseDate() != null && updatedBook.getReleaseDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Дата выпуска не может быть в будущем.");
        }

        BookCategory category = bookCategoryRepository.findById(Math.toIntExact(updatedBook.getCategoryId()))
                .orElseThrow(() -> new RuntimeException("Категория не найдена с id: " + updatedBook.getCategoryId()));

        existingBook.setBookName(updatedBook.getBookName());
        existingBook.setAuthorName(updatedBook.getAuthorName());
        existingBook.setReleaseDate(updatedBook.getReleaseDate());
        existingBook.setBookImage(updatedBook.getBookImage());
        existingBook.setBookCategory(category);

        // 🔹 Обновление количества в стоке
        if (updatedBook.getQuantity() != null) {
            Stock stock = existingBook.getStock();
            if (stock == null) {
                stock = Stock.builder()
                        .book(existingBook)
                        .quantity(updatedBook.getQuantity())
                        .build();
                existingBook.setStock(stock);
            } else {
                stock.setQuantity(updatedBook.getQuantity());
            }
        }

        bookRepository.save(existingBook);
    }


    @Override
    public void deleteBookById(Long id) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книга не найдена с id: " + id));

        // 🔹 Проверяем, взята ли книга пользователем
        if (borrowedBookRepository.existsByBook(existingBook)) {
            throw new IllegalStateException("Эта книга не может быть удалена, пока она взята пользователем.");
        }

        bookRepository.delete(existingBook);
    }


    @Override
    public List<Book> getBooksByCategory(Long genreId) {
        return bookRepository.findByCategoryId(genreId);
    }

}
