package com.example.gitlearningprojectwithspringandreact.controllers;

import com.example.gitlearningprojectwithspringandreact.entities.Book;
import com.example.gitlearningprojectwithspringandreact.entities.BookCreateDTO;
import com.example.gitlearningprojectwithspringandreact.services.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookServiceImpl bookService;

    @GetMapping("/get-all-books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // Получить книгу по ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    // Добавить новую книгу
    @PostMapping("/addBook")
    public ResponseEntity<Void> addBook(@RequestBody BookCreateDTO request) {
        bookService.saveBook(request);
        return ResponseEntity.ok().build();
    }


    // Обновить книгу
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable("id") Long id,
                                           @RequestBody BookCreateDTO updatedBook) {
        bookService.updateBook(id, updatedBook);
        return ResponseEntity.ok().build();
    }



    // Удалить книгу
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.ok().build();
    }




}
