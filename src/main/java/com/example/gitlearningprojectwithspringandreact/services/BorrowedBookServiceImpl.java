package com.example.gitlearningprojectwithspringandreact.services;

import com.example.gitlearningprojectwithspringandreact.entities.Book;
import com.example.gitlearningprojectwithspringandreact.entities.BorrowedBook;
import com.example.gitlearningprojectwithspringandreact.entities.BorrowedBookDTO;
import com.example.gitlearningprojectwithspringandreact.entities.User;
import com.example.gitlearningprojectwithspringandreact.repositories.BorrowedBookRepository;
import com.example.gitlearningprojectwithspringandreact.repositories.bookRepository.BookRepository;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class BorrowedBookServiceImpl implements BorrowedBookService {

    private final BorrowedBookRepository borrowedBookRepository;
    private final BookRepository bookRepository;
    private final StockService stockService;

    public BorrowedBookServiceImpl(BorrowedBookRepository borrowedBookRepository,
                                   BookRepository bookRepository,
                                   StockService stockService) {
        this.borrowedBookRepository = borrowedBookRepository;
        this.bookRepository = bookRepository;
        this.stockService = stockService;
    }

    @Override
    public void borrowBook(User user, Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Книга не найдена с id: " + bookId));

        // 🔹 Проверка: не больше 10 книг
        if (borrowedBookRepository.countByUser(user) >= 10) {
            throw new IllegalStateException("Вы не можете взять больше 10 книг одновременно.");
        }

        // 🔹 Проверка: юзер уже взял эту книгу
        if (borrowedBookRepository.existsByUserAndBook(user, book)) {
            throw new IllegalStateException("Эта книга уже у вас на руках.");
        }

        // 🔹 Проверка: наличие в стоке
        stockService.decreaseStock(bookId, 1);

        BorrowedBook borrowedBook = BorrowedBook.builder()
                .user(user)
                .book(book)
                .borrowedAt(LocalDateTime.now())
                .build();

        borrowedBookRepository.save(borrowedBook);
    }

    @Override
    public void returnBook(User user, Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Книга не найдена с id: " + bookId));

        BorrowedBook borrowedBook = borrowedBookRepository.findByUserAndBook(user, book)
                .orElseThrow(() -> new IllegalStateException("Эта книга не числится за вами."));

        borrowedBookRepository.delete(borrowedBook);

        // 🔹 Вернули → сток увеличился
        stockService.increaseStock(bookId, 1);
    }

    @Override
    public List<Book> getUserBorrowedBooks(User user) {
        return borrowedBookRepository.findByUser(user)
                .stream()
                .map(BorrowedBook::getBook)
                .toList();
    }
    @Override
    public List<BorrowedBookDTO> getUserBorrowedBooksDTO(User user) {
        boolean isAdmin = "ROLE_ADMIN".equals(user.getRole());

        List<BorrowedBook> borrowedBooks = isAdmin
                ? borrowedBookRepository.findAll()
                : borrowedBookRepository.findByUser(user);

        return borrowedBooks.stream()
                .map(bb -> toDTO(bb, isAdmin))
                .toList();
    }

    private BorrowedBookDTO toDTO(BorrowedBook bb, boolean isAdmin) {
        return BorrowedBookDTO.builder()
                .bookId((long) bb.getBook().getBookId())
                .bookName(bb.getBook().getBookName())
                .authorName(bb.getBook().getAuthorName())
                .genre(bb.getBook().getBookCategory() != null
                        ? bb.getBook().getBookCategory().getGenre()
                        : "Не указан")
                .bookImage(bb.getBook().getBookImage())
                .userEmail(isAdmin ? bb.getUser().getEmail() : null)
                .borrowedAt(bb.getBorrowedAt())
                .build();
    }


}


