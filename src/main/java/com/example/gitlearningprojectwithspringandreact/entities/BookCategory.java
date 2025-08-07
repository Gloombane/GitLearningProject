package com.example.gitlearningprojectwithspringandreact.entities;
import jakarta.persistence.*;
import lombok.*;



@Entity
@Table(name = "book_categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BookCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "genre", nullable = false, unique = true)
    private String genre;

}






