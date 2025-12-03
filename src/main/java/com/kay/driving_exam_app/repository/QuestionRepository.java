package com.kay.driving_exam_app.repository;

import com.kay.driving_exam_app.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {
    List<Question> findByCategory(String category);


    @Query(value = "SELECT * FROM question q WHERE q.category=:category ORDER BY RANDOM() LIMIT :num",nativeQuery = true)
    List<Question> findByCategoryRandom(String category, int num);

    List<Question> findByQuestionText(String questionText);
}
