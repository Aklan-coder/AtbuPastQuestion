package com.Atbu.AtbuPastQuestion.dao;

import com.Atbu.AtbuPastQuestion.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
