package com.Atbu.AtbuPastQuestion.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "question")
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question")
    private String text;

    @Lob
    @Column(name="image", length = 1000)
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
