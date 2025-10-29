package com.example.gitlearningprojectwithspringandreact.entities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
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
    @PastOrPresent(message = "Дата выпуска не может быть в будущем.")
    private LocalDate releaseDate;
    private String bookImage;
    private Long categoryId;
    @Min(value = 0, message = "Количество не может быть отрицательным")
    private Integer quantity;
}