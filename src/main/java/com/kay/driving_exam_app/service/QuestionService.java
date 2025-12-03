package com.kay.driving_exam_app.service;


import com.kay.driving_exam_app.exceptions.ResourceAvailableException;
import com.kay.driving_exam_app.exceptions.ResourceNotFoundException;
import com.kay.driving_exam_app.model.Question;
import com.kay.driving_exam_app.model.QuestionResponse;
import com.kay.driving_exam_app.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    @Autowired
    private QuestionRepository questionRepository;

    public List<QuestionResponse> getQuestions() {
        List<Question> questions = questionRepository.findAll();
        if (questions.isEmpty()) {
            throw new ResourceNotFoundException("Questions not yet loaded");
        }

        return questions
                .stream()
                .map(quest -> new QuestionResponse(
                        quest.getQuestion_id(),
                        quest.getCategory(),
                        quest.getQuestionText(),
                        quest.getOptionA(),
                        quest.getOptionB(),
                        quest.getOptionC(),
                        quest.getOptionD())).toList();
    }

    public List<QuestionResponse> questionsByCategory(String category) {
        List<Question> quest = questionRepository.findByCategory(category);

        if (quest.isEmpty()) {
            throw new ResourceNotFoundException("There are no Questions on " + category + "loaded !!!");
        }

        return quest.stream().map(response ->new QuestionResponse(
                        response.getQuestion_id(),
                        response.getCategory(),
                        response.getQuestionText(),
                        response.getOptionA(),
                        response.getOptionB(),
                        response.getOptionC(),
                        response.getOptionD())).toList();
    }


    public String updateQuestion(Long id,Question question) {
        Question savedquestion = questionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Question not found"));
        if(savedquestion.getQuestionText().equalsIgnoreCase(question.getQuestionText())) {
            savedquestion.setCategory(question.getCategory());
            savedquestion.setCorrectAnswer(question.getCorrectAnswer());
            savedquestion.setOptionA(question.getOptionA());
            savedquestion.setOptionB(question.getOptionB());
            savedquestion.setOptionC(question.getOptionC());
            savedquestion.setOptionD(question.getOptionD());
            savedquestion.setQuestionText(question.getQuestionText());
        }

        questionRepository.save(savedquestion);
        return "Question updated successfully";
    }

    public QuestionResponse createQuestion(Question question) {

        List<Question> questions = questionRepository.findByCategory(question.getCategory());
        if(questions.stream().anyMatch(c->c.getQuestionText().equalsIgnoreCase(question.getQuestionText()))){
            throw new ResourceAvailableException(question.getQuestionText());
        }
        questionRepository.save(question);
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setQuestion_id(question.getQuestion_id());
        questionResponse.setCategory(question.getCategory());
        questionResponse.setQuestionText(question.getQuestionText());
        questionResponse.setOptionA(question.getOptionA());
        questionResponse.setOptionB(question.getOptionB());
        questionResponse.setOptionC(question.getOptionC());
        questionResponse.setOptionD(question.getOptionD());

        return questionResponse;
    }

    public String deleteQuestion(Long id) {
        var user = questionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Question not found"));
        questionRepository.deleteById(id);
        return "Question deleted successfully";
    }
}
