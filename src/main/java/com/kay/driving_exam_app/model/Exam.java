package com.kay.driving_exam_app.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "exam")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exam_id;
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "exam_question",
    joinColumns = @JoinColumn(name = "exam_id"),
    inverseJoinColumns = @JoinColumn(name="question_id"))
    private List<Question> questions = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},mappedBy = "exam")
    private List<User> users = new ArrayList<>();
}
