package com.kay.driving_exam_app.controller;



import com.kay.driving_exam_app.model.QuestionResponse;
import com.kay.driving_exam_app.model.Response;
import com.kay.driving_exam_app.service.ExamService;
import jakarta.validation.Valid;
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


    @PostMapping("/admin/exam/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createQuiz(@RequestParam String category,@RequestParam int num, @RequestParam String name){
        var message = examServ.createQuiz(category,num,name);
        return ResponseEntity.ok(message);

    }


    @GetMapping("/public/quiz/questions/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<QuestionResponse>> getExam(@PathVariable Long id){
        return ResponseEntity.ok(examServ.getExam(id));
    }


    @PostMapping("/public/submit/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Integer> getScore(@PathVariable Long id,@Valid @RequestBody List<Response> responses){
        Integer results = examServ.calculateResult(id,responses);
        return ResponseEntity.ok(results);
    }
}
