package com.kay.driving_exam_app.controller;


import com.kay.driving_exam_app.model.Question;
import com.kay.driving_exam_app.model.QuestionResponse;
import com.kay.driving_exam_app.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QuestionController {
    @Autowired
    private QuestionService questionServ;


    @GetMapping("/questions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<QuestionResponse>>getAllQuestions(){
        return questionServ.getAllQuestions();

    }


    @GetMapping("/category/{category}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<QuestionResponse>> getQuestionByCategory(@PathVariable String category){
       return questionServ.getQuestionByCategory(category);

    }


    @PutMapping("/updatequestion")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addUpdateOneQuestion(@RequestBody Question question){
        return questionServ.updateQuestion(question);
    }


    @PostMapping("/addquestions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<QuestionResponse>> addMoreQuestion(@RequestBody List<Question> question)
    {
        return questionServ.addMorethanOneQuestions(question);
    }


    @DeleteMapping("/deleteQuestion/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long id){
        return questionServ.deleteQuestion(id);
    }
}
