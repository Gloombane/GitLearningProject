package com.example.gitlearningprojectwithspringandreact.services.bookCategoryService;

import com.example.gitlearningprojectwithspringandreact.entities.BookCategory;
import com.example.gitlearningprojectwithspringandreact.entities.CategoryDTO;

import java.util.List;
import java.util.Optional;

public interface BookCategoryService {
    BookCategory createCategory(CategoryDTO categoryDTO);
    void updateCategory(int id, CategoryDTO categoryDTO);
    void deleteCategory(int id);
    List<BookCategory> getAllCategories();
    BookCategory findById(int id);
    Optional<BookCategory> findByGenreIgnoreCase(String genre);
}
