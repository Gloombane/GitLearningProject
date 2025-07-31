package com.example.gitlearningprojectwithspringandreact.services;

import com.example.gitlearningprojectwithspringandreact.entities.Book;
import org.springframework.stereotype.Service;
import com.example.gitlearningprojectwithspringandreact.repositories.BookRepository;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Получить все книги
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Получить книгу по ID
    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книга не найдена с id: " + id));
    }

    // Сохранить новую книгу
    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    // Обновить существующую книгу
    @Override
    public void updateBook(Long id, Book updatedBook) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книга не найдена с id: " + id));

        existingBook.setBookName(updatedBook.getBookName());
        existingBook.setBookImage(updatedBook.getBookImage());
        existingBook.setAuthorName(updatedBook.getAuthorName());
        existingBook.setReleaseDate(updatedBook.getReleaseDate());

        bookRepository.save(existingBook);
    }

    // Удалить книгу
    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
