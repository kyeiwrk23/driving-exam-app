package com.kay.driving_exam_app.repository;


import com.kay.driving_exam_app.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ExamDao extends JpaRepository<Exam,Long> {

}
