package com.kay.driving_exam_app.controller;


import com.kay.driving_exam_app.model.Question;
import com.kay.driving_exam_app.model.QuestionResponse;
import com.kay.driving_exam_app.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QuestionController {
    @Autowired
    private QuestionService questionService;


    @GetMapping("/admin/questions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<QuestionResponse>>getQuestions(){
        return ResponseEntity.ok(questionService.getQuestions());
    }


    @GetMapping("/admin/category/{category}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<QuestionResponse>> getCategoryQuestions(@PathVariable String category){
       return ResponseEntity.ok(questionService.questionsByCategory(category));

    }


    @PutMapping("/admin/update/question/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> UpdateQuestion(@PathVariable Long id ,@Valid @RequestBody Question question){
        return ResponseEntity.ok(questionService.updateQuestion(id,question));
    }


    @PostMapping("/admin/create/question")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<QuestionResponse> createQuestion(@Valid @RequestBody Question question)
    {
        return ResponseEntity.ok(questionService.createQuestion(question));
    }


    @DeleteMapping("/admin/delete/Question/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long id){
        return ResponseEntity.ok(questionService.deleteQuestion(id));
    }
}
