package com.example.gitlearningprojectwithspringandreact.services;

import com.example.gitlearningprojectwithspringandreact.entities.Book;
import com.example.gitlearningprojectwithspringandreact.entities.BorrowedBookDTO;
import com.example.gitlearningprojectwithspringandreact.entities.User;

import java.util.List;

public interface BorrowedBookService {
    void borrowBook(User user, Long bookId);
    void returnBook(User user, Long bookId);
    List<Book> getUserBorrowedBooks(User user);
    List<BorrowedBookDTO> getUserBorrowedBooksDTO(User user);
}
