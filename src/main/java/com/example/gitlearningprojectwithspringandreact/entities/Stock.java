package com.example.gitlearningprojectwithspringandreact.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stock")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long stockId;

    // Количество доступных экземпляров книги
    @Column(name = "quantity", nullable = false)
    private int quantity;

    // Связь "один-к-одному" с книгой
    @OneToOne
    @JoinColumn(name = "book_id", nullable = false, unique = true)
    private Book book;
}
