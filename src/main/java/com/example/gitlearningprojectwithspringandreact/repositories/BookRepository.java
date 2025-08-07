package com.example.gitlearningprojectwithspringandreact.repositories;

import com.example.gitlearningprojectwithspringandreact.entities.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    @Query("SELECT b FROM Book b JOIN FETCH b.bookCategory")
    List<Book> findAllWithCategory();

}
