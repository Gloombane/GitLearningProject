package com.example.gitlearningprojectwithspringandreact.services;

import com.example.gitlearningprojectwithspringandreact.entities.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(Long id);
    void saveBook(Book book);
    void updateBook(Long id, Book updatedBook);
    void deleteBookById(Long id);
}
