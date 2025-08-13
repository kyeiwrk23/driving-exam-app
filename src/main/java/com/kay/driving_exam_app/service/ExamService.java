package com.kay.driving_exam_app.service;


import com.kay.driving_exam_app.model.Exam;
import com.kay.driving_exam_app.model.Question;
import com.kay.driving_exam_app.model.QuestionResponse;
import com.kay.driving_exam_app.model.Response;
import com.kay.driving_exam_app.repository.ExamDao;
import com.kay.driving_exam_app.repository.QuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {
    @Autowired
    private ExamDao examDao;
    @Autowired
    private QuestionDto questionDto;
    public ResponseEntity<String> createQuiz(String category,int num,String name) {
        List<Question> questionList = questionDto.findByCategoryRandom(category,num);
        Exam exam = new Exam();
        exam.setName(name);
        exam.setQuestions(questionList);
        examDao.save(exam);

        return new ResponseEntity<>("created", HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionResponse>> getQuizById(Long id) {
        Exam exam = examDao.findById(id).get();
        List<Question> question = exam.getQuestions();

        List<QuestionResponse> questRes = question
                        .stream()
                      .map(quest->new QuestionResponse(
                        quest.getQuestion_id(),
                        quest.getCategory(),
                        quest.getQuestionText(),
                        quest.getOptionA(),
                        quest.getOptionB(),
                        quest.getOptionC(),
                        quest.getOptionD())).toList();

        return  new ResponseEntity<>(questRes,HttpStatus.OK);
    }

    public ResponseEntity<?> calculateResult(Long id,List<Response> responses) {
        Response res = responses.get(0);
        System.out.println("id: "+res.getId()+" answer:"+res.getAnswer());
        int tag = 0;
        Exam exam = examDao.findById(id).get();
        List<Question> questions = exam.getQuestions();
        for(Response response: responses) {
            for(Question question : questions) {
                if (response.getAnswer().equals(question.getCorrectAnswer())) {
                    tag++;
                }
            }
        }

        return new ResponseEntity<>(tag,HttpStatus.OK);
    }
}
