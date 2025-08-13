package com.kay.driving_exam_app.service;


import com.kay.driving_exam_app.model.Question;
import com.kay.driving_exam_app.model.QuestionResponse;
import com.kay.driving_exam_app.repository.QuestionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);
    @Autowired
    private QuestionDto questionDto;
    public ResponseEntity<List<QuestionResponse>> getAllQuestions() {
        List<Question> questions = questionDto.findAll();
        List<QuestionResponse> res = questions
                .stream()
                .map(quest ->new QuestionResponse(
                        quest.getQuestion_id(),
                        quest.getCategory(),
                        quest.getQuestionText(),
                        quest.getOptionA(),
                        quest.getOptionB(),
                        quest.getOptionC(),
                        quest.getOptionD())).toList();
        try {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<List<QuestionResponse>> getQuestionByCategory(String category) {
        List<Question> quest = questionDto.findByCategory(category);

        if(category == null || category.isEmpty() || quest.isEmpty()){
            logger.info("The category value is empty or doesn't exist");
        }

        List<QuestionResponse> questRes = quest.stream().map(response ->new QuestionResponse(
                        response.getQuestion_id(),
                        response.getCategory(),
                        response.getQuestionText(),
                        response.getOptionA(),
                        response.getOptionB(),
                        response.getOptionC(),
                        response.getOptionD())).toList();

        try {
            return new ResponseEntity<>(questRes,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<String> updateQuestion(Question question) {
        Question getquestion = questionDto.findByQuestionText(question.getQuestionText());
        if(getquestion.getQuestionText().equalsIgnoreCase(question.getQuestionText())) {
            getquestion.setCategory(question.getCategory());
            getquestion.setCorrectAnswer(question.getCorrectAnswer());
            getquestion.setOptionA(question.getOptionA());
            getquestion.setOptionB(question.getOptionB());
            getquestion.setOptionC(question.getOptionC());
            getquestion.setOptionD(question.getOptionD());
            getquestion.setQuestionText(question.getQuestionText());
        }

            questionDto.save(getquestion);

        try {
            return new ResponseEntity<>("Updated",HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<List<QuestionResponse>> addMorethanOneQuestions(List<Question> question) {
        if(question.isEmpty())
            logger.info("The list of questions are empty");
        List<Question> quest = questionDto.saveAll(question);

        List<QuestionResponse> questRes = quest.stream().map(response ->new QuestionResponse(
                response.getQuestion_id(),
                response.getCategory(),
                response.getQuestionText(),
                response.getOptionA(),
                response.getOptionB(),
                response.getOptionC(),
                response.getOptionD())).toList();

        try {
            return new ResponseEntity<>(questRes,HttpStatus.CREATED);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<String> deleteQuestion(Long id) {
        questionDto.deleteById(id.intValue());
        return ResponseEntity.ok("Deleted");
    }
}
