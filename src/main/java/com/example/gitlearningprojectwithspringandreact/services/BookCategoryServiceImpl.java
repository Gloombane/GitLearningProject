package com.example.gitlearningprojectwithspringandreact.services;


import com.example.gitlearningprojectwithspringandreact.entities.BookCategory;
import com.example.gitlearningprojectwithspringandreact.entities.CategoryDTO;
import com.example.gitlearningprojectwithspringandreact.repositories.BookCategoryRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BookCategoryServiceImpl implements BookCategoryService {

    private final BookCategoryRepository bookCategoryRepository;

    @Override
    public BookCategory createCategory(CategoryDTO categoryDTO) {
        Optional<BookCategory> existingCategory =
                bookCategoryRepository.findByGenreIgnoreCase(categoryDTO.getGenre());

        if (existingCategory.isPresent()) {
            throw new RuntimeException("Категория с таким жанром уже существует");
        }

        BookCategory category = BookCategory.builder()
                .genre(categoryDTO.getGenre())
                .build();

        return bookCategoryRepository.save(category);
    }



    @Override
    public void updateCategory(int id, CategoryDTO categoryDTO) {
        BookCategory existing = bookCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Категория не найдена с id: " + id));

        existing.setGenre(categoryDTO.getGenre());
        bookCategoryRepository.save(existing);
    }

    @Override
    public void deleteCategory(int id) {
        BookCategory existing = bookCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Категория не найдена с id: " + id));

        bookCategoryRepository.delete(existing);
    }

    @Override
    public List<BookCategory> getAllCategories() {
        return bookCategoryRepository.findAll();
    }

    @Override
    public BookCategory findById(int id) {
        return bookCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Категория не найдена с id: " + id));
    }

    @Override
    public Optional<BookCategory> findByGenreIgnoreCase(String genre) {
        return bookCategoryRepository.findByGenreIgnoreCase(genre);
    }
}
