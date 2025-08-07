package com.example.gitlearningprojectwithspringandreact.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;

    @Column(name = "book_name", nullable = false)
    private String bookName;

    @Column(name = "book_image")
    private String bookImage;

    @Column(name = "author_name", nullable = false)
    private String authorName;

    @Column(name = "release_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private BookCategory bookCategory;

}






