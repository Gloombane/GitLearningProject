package com.example.gitlearningprojectwithspringandreact.services;

public interface StockService {
    void increaseStock(Long bookId, int count);
    void decreaseStock(Long bookId, int count);
}
