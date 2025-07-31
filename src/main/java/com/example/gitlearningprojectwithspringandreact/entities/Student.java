package com.example.gitlearningprojectwithspringandreact.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private int studentId;

    @Column(name = "student_name", nullable = false)
    private String studentName;

    @Column(name = "student_last_name", nullable = false)
    private String studentLastName;

    @Column(name = "student_group")
    private String studentGroup;

}
