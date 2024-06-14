package com.example.exam.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "statements")
public class Statement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private String numberCar;
    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
