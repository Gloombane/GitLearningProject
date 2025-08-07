package com.example.gitlearningprojectwithspringandreact.controllers;

import com.example.gitlearningprojectwithspringandreact.entities.BookCategory;
import com.example.gitlearningprojectwithspringandreact.entities.CategoryDTO;
import com.example.gitlearningprojectwithspringandreact.services.BookCategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/categories")
public class BookCategoryController {

    @Autowired
    private final BookCategoryServiceImpl bookCategoryService;

    public BookCategoryController(BookCategoryServiceImpl bookCategoryService) {
        this.bookCategoryService = bookCategoryService;
    }

    // 🔹 Получить все категории
    @GetMapping("/get-all-categories")
    public ResponseEntity<List<BookCategory>> getAllCategories() {
        List<BookCategory> categories = bookCategoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }


    // 🔹 Получить категорию по ID
    @GetMapping("/get-category/{id}")
    public ResponseEntity<BookCategory> getCategoryById(@PathVariable int id) {
        BookCategory category = bookCategoryService.findById(id);
        return ResponseEntity.ok(category);
    }


    // 🔹 Добавить новую категорию
    @PostMapping("/add-category")
    public ResponseEntity<?> addCategory(@RequestBody CategoryDTO categoryDTO) {
        boolean created = bookCategoryService.createCategory(categoryDTO);

        if (!created) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Категория с таким жанром уже существует"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    // 🔹 Обновить категорию
    @PutMapping("/update-category/{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable int id, @RequestBody CategoryDTO categoryDTO) {
        bookCategoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok().build();
    }

    // 🔹 Удалить категорию
    @DeleteMapping("/delete-category/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int id) {
        bookCategoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}


