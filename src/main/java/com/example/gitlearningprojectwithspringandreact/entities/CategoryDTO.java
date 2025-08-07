package com.example.gitlearningprojectwithspringandreact.entities;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDTO {
    private int id;
    private String genre;
}

