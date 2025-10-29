package com.example.gitlearningprojectwithspringandreact.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long bookId;
    private String bookName;
    private String authorName;
    private LocalDate releaseDate;
    private String bookImage;
    private Long categoryId;
    private String genre;
    private Integer quantity;
}

