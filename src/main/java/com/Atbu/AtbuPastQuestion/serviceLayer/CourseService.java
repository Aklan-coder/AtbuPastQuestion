package com.Atbu.AtbuPastQuestion.serviceLayer;

import com.Atbu.AtbuPastQuestion.dao.CourseRepository;
import com.Atbu.AtbuPastQuestion.entity.Course;
import com.Atbu.AtbuPastQuestion.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    // Method to find all courses from the database
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Method to find  courses by id  from the database
    public Optional<Course> getCourseById(Long courseId) {
        return courseRepository.findById(courseId);
    }

    // Method to add  course to the database
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    // Method to UPDATE course existing  in the database
    public Course updateCourse(Long courseId, Course updatedCourse) {
        Course existingCourse = getCourseById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        existingCourse.setCode(updatedCourse.getCode());
        return courseRepository.save(existingCourse);
    }

    // Method to DELETE course from the database
    public void deleteCourse(Long courseId) {
        Course course = getCourseById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        courseRepository.delete(course);
    }

}
