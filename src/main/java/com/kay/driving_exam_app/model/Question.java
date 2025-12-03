package com.kay.driving_exam_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long question_id;
    @NotNull(message = "Category Can't be Null")
    private String category;
    @NotNull(message = "Question can't be Null")
    private String questionText;
    @NotNull(message = "Answer option A can't be Null")
    private String optionA;
    @NotNull(message = "Answer option B can't be Null")
    private String optionB;
    @NotNull(message = "Answer option C can't be Null")
    private String optionC;
    @NotNull(message = "Answer option D can't be Null")
    private String optionD;
    @NotNull(message = "Correct Answer can't be Null")
    private String correctAnswer;

    @ManyToMany(mappedBy = "questions")
    private List<Exam> exams = new ArrayList<>();
}
