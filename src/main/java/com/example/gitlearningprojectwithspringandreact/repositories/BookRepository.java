package com.example.gitlearningprojectwithspringandreact.repositories;

import com.example.gitlearningprojectwithspringandreact.entities.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    @Query("SELECT b FROM Book b JOIN FETCH b.bookCategory")
    List<Book> findAllWithCategory();

    @Query("SELECT b FROM Book b WHERE (:genreId IS NULL OR b.bookCategory.categoryId = :genreId)")
    List<Book> findByCategoryId(@Param("genreId") Long genreId);

}
