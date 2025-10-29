package com.example.gitlearningprojectwithspringandreact.controllers;

import com.example.gitlearningprojectwithspringandreact.entities.*;
import com.example.gitlearningprojectwithspringandreact.services.BorrowedBookServiceImpl;
import com.example.gitlearningprojectwithspringandreact.services.bookService.BookServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private BorrowedBookServiceImpl borrowedBookService;
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    // Получить книги с фильтрацией
    @GetMapping
    public List<BookDTO> getBooksByFilter(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long genreId
    ) {
        List<Book> books;
        if (genreId != null) {
            books = bookService.getBooksByCategory(genreId);
        } else {
            books = bookService.getAllBooks();
        }
        logger.info("Logger starting ");
        logger.debug("Детали запроса к /api/books");
        logger.debug(books +" booksDB");
        logger.info(books +" info ");
        // Если есть поисковый запрос
        if (search != null && !search.isBlank()) {
            String query = search.toLowerCase();
            books = books.stream()
                    .filter(b -> b.getBookName().toLowerCase().contains(query)
                            || b.getAuthorName().toLowerCase().contains(query))
                    .toList();
        }

        return books.stream().map(this::toDTO).toList();
    }

    // Получить книгу по ID
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(toDTO(book));
    }

    @PostMapping("/addBook")
    public ResponseEntity<Void> addBook(@RequestBody BookCreateDTO request) {
        bookService.saveBook(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable("id") Long id,
                                           @Valid @RequestBody BookCreateDTO updatedBook) {
        bookService.updateBook(id, updatedBook);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{bookId}/borrow")
    public ResponseEntity<?> borrowBook(@PathVariable Long bookId,
                                        @AuthenticationPrincipal User user) {
        try {
            borrowedBookService.borrowBook(user, bookId);
            return ResponseEntity.ok("Книга успешно взята.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{bookId}/return")
    public ResponseEntity<?> returnBook(@PathVariable Long bookId,
                                        @AuthenticationPrincipal User user) {
        try {
            borrowedBookService.returnBook(user, bookId);
            return ResponseEntity.ok("Книга успешно возвращена.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/my")
    public ResponseEntity<List<BorrowedBookDTO>> myBooks(@AuthenticationPrincipal User user) {
        List<BorrowedBookDTO> books = borrowedBookService.getUserBorrowedBooksDTO(user);
        return ResponseEntity.ok(books);
    }

    private BookDTO toDTO(Book book) {
        Long categoryId = null;
        String genre = "Не указан";

        if (book.getBookCategory() != null) {
            categoryId = (long) book.getBookCategory().getCategoryId();
            genre = book.getBookCategory().getGenre();
        }

        Integer quantity = (book.getStock() != null) ? book.getStock().getQuantity() : 0;

        return new BookDTO(
                (long) book.getBookId(),
                book.getBookName(),
                book.getAuthorName(),
                book.getReleaseDate(),
                book.getBookImage(),
                categoryId,
                genre,
                quantity
        );
    }


}

