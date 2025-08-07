package com.example.gitlearningprojectwithspringandreact.services;

import com.example.gitlearningprojectwithspringandreact.entities.BookCategory;
import com.example.gitlearningprojectwithspringandreact.entities.CategoryDTO;

import java.util.List;

public interface BookCategoryService {
    boolean createCategory(CategoryDTO categoryDTO);
    void updateCategory(int id, CategoryDTO categoryDTO);
    void deleteCategory(int id);
    List<BookCategory> getAllCategories();
    BookCategory findById(int id);

}
