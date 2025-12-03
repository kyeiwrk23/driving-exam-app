package com.kay.driving_exam_app.repository;


import com.kay.driving_exam_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserByEmail(String email);

    boolean existsUserByEmail(String email);
}
