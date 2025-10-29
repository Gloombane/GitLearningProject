package com.example.gitlearningprojectwithspringandreact.entities;

import lombok.*;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BorrowedBookDTO {
    private Long bookId;
    private String bookName;
    private String authorName;
    private String bookImage;
    private String genre;

    private String userEmail;   // кто взял
    private LocalDateTime borrowedAt;

}
