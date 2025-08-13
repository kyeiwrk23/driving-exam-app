package com.kay.driving_exam_app.repository;

import com.kay.driving_exam_app.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDto extends JpaRepository<Question,Integer> {
    List<Question> findByCategory(String category);

    //@Query(value = "SELECT * FROM question q WHERE question_text=:questionTest", nativeQuery = true)
    Question findByQuestionText(String text);

    @Query(value = "SELECT * FROM question q WHERE q.category=:category ORDER BY RANDOM() LIMIT :num",nativeQuery = true)
    List<Question> findByCategoryRandom(String category, int num);

}
