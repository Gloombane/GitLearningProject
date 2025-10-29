package com.example.gitlearningprojectwithspringandreact.repositories.bookCategoryRepository;


import com.example.gitlearningprojectwithspringandreact.entities.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, Integer> {
    Optional<BookCategory> findByGenreIgnoreCase(String genre);

}
