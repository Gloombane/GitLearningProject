package com.example.gitlearningprojectwithspringandreact.services;

import com.example.gitlearningprojectwithspringandreact.entities.Stock;
import com.example.gitlearningprojectwithspringandreact.repositories.stockRepository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService{

    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void increaseStock(Long bookId, int count) {
        Stock stock = stockRepository.findByBook_BookId(bookId)
                .orElseThrow(() -> new RuntimeException("Сток не найден для книги id=" + bookId));
        stock.setQuantity(stock.getQuantity() + count);
        stockRepository.save(stock);
    }

    @Override
    public void decreaseStock(Long bookId, int count) {
        Stock stock = stockRepository.findByBook_BookId(bookId)
                .orElseThrow(() -> new RuntimeException("Сток не найден для книги id=" + bookId));
        if (stock.getQuantity() < count) {
            throw new RuntimeException("Недостаточно экземпляров книги");
        }
        stock.setQuantity(stock.getQuantity() - count);
        stockRepository.save(stock);
    }
}
