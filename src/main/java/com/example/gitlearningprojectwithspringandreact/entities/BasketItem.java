package com.example.gitlearningprojectwithspringandreact.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "basket_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasketItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basket_item_id")
    private Long basketItemId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Ссылка на пользователя

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "quantity")
    private int quantity = 1; 
}

