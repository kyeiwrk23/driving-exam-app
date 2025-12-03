package com.kay.driving_exam_app.service;



import com.kay.driving_exam_app.exceptions.ResourceNotFoundException;
import com.kay.driving_exam_app.model.Exam;
import com.kay.driving_exam_app.model.Question;
import com.kay.driving_exam_app.model.QuestionResponse;
import com.kay.driving_exam_app.model.Response;
import com.kay.driving_exam_app.repository.ExamRepository;
import com.kay.driving_exam_app.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private QuestionRepository  questionRepository;

    public String createQuiz(String category,int num,String name) {
        List<Question> questionList = questionRepository.findByCategoryRandom(category,num);
        if(questionList.isEmpty()){
            throw new ResourceNotFoundException("Question not found");
        }
        Exam exam = new Exam();
        exam.setName(name);
        exam.setQuestions(questionList);
        examRepository.save(exam);

        return "Exam created";
    }

    public List<QuestionResponse> getExam(Long id) {
        Exam exam = examRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Exams not found"));
        List<Question> questions = exam.getQuestions();

        List<QuestionResponse> questionResponse = questions
                        .stream()
                      .map(quest->new QuestionResponse(
                        quest.getQuestion_id(),
                        quest.getCategory(),
                        quest.getQuestionText(),
                        quest.getOptionA(),
                        quest.getOptionB(),
                        quest.getOptionC(),
                        quest.getOptionD())).toList();

        return questionResponse;
    }

    public Integer calculateResult(Long id,List<Response> responses) {
        Response res = responses.get(0);
        System.out.println("id: "+res.getId()+" answer:"+res.getAnswer());
        int tag = 0;
        Exam exam = examRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Exams not found"));
        List<Question> questions = exam.getQuestions();
        if(questions.isEmpty()){
            throw new ResourceNotFoundException("Exam " + exam.getName() + "have no questions");
        }
        for(Response response: responses) {
            for(Question question : questions) {
                if (response.getAnswer().equals(question.getCorrectAnswer())) {
                    tag++;
                }
            }
        }

        return tag;

    }
}
