package com.example.gitlearningprojectwithspringandreact.entities;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookCreateDTO {
    private String bookName;
    private String authorName;
    private LocalDate releaseDate;
    private String bookImage;
    private Long categoryId;

}