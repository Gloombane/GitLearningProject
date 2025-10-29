package com.example.gitlearningprojectwithspringandreact.repositories.stockRepository;

import com.example.gitlearningprojectwithspringandreact.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByBook_BookId(Long bookId);
}

