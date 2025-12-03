package com.kay.driving_exam_app.repository;

import com.kay.driving_exam_app.model.Role;
import com.kay.driving_exam_app.model.RoleImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByRoleName(RoleImpl roleName);
}
