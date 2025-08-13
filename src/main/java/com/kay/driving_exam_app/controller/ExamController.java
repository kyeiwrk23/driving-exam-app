package com.kay.driving_exam_app.controller;



import com.kay.driving_exam_app.model.QuestionResponse;
import com.kay.driving_exam_app.model.Response;
import com.kay.driving_exam_app.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExamController {

    @Autowired
    private ExamService examServ;


    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createQuiz(@RequestParam String category,@RequestParam int num, @RequestParam String name){
         return examServ.createQuiz(category,num,name);

    }


    @GetMapping("/getquizquestion/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<QuestionResponse>> getQuizById(@PathVariable Long id){
        return examServ.getQuizById(id);
    }


    @PostMapping("/submit/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getScore(@PathVariable Long id,@RequestBody List<Response> responses){
        return examServ.calculateResult(id,responses);
    }
}
