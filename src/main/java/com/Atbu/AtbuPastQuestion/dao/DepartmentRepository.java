package com.Atbu.AtbuPastQuestion.dao;

import com.Atbu.AtbuPastQuestion.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
