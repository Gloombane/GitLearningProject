package com.example.gitlearningprojectwithspringandreact.services;

import com.example.gitlearningprojectwithspringandreact.entities.Book;
import com.example.gitlearningprojectwithspringandreact.entities.BookCategory;
import com.example.gitlearningprojectwithspringandreact.entities.BookCreateDTO;
import com.example.gitlearningprojectwithspringandreact.repositories.BookCategoryRepository;
import org.springframework.stereotype.Service;
import com.example.gitlearningprojectwithspringandreact.repositories.BookRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookCategoryRepository bookCategoryRepository;

    public BookServiceImpl(BookRepository bookRepository, BookCategoryRepository bookCategoryRepository) {
        this.bookRepository = bookRepository;
        this.bookCategoryRepository = bookCategoryRepository;
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

        bookRepository.save(book);
    }



    // Обновить существующую книгу
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

        bookRepository.save(existingBook);
    }


    // Удалить книгу
    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> getBooksByCategory(Long genreId) {
        return bookRepository.findByCategoryId(genreId);
    }
}
