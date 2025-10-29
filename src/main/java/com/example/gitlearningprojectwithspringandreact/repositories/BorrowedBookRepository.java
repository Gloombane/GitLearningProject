package com.example.gitlearningprojectwithspringandreact.repositories;

import com.example.gitlearningprojectwithspringandreact.entities.Book;
import com.example.gitlearningprojectwithspringandreact.entities.BorrowedBook;
import com.example.gitlearningprojectwithspringandreact.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {

    long countByUser(User user);

    boolean existsByUserAndBook(User user, Book book);

    List<BorrowedBook> findByUser(User user);

    Optional<BorrowedBook> findByUserAndBook(User user, Book book);
    boolean existsByBook(Book book);
}

