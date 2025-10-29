package com.example.gitlearningprojectwithspringandreact.services.bookService;

import com.example.gitlearningprojectwithspringandreact.entities.Book;
import com.example.gitlearningprojectwithspringandreact.entities.BookCreateDTO;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(Long id);
    void saveBook(BookCreateDTO request);
    void updateBook(Long id, BookCreateDTO updatedBook);
    void deleteBookById(Long id);
    List<Book> getBooksByCategory(Long genreId);

}
