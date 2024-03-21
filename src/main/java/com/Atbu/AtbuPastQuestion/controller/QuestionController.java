package com.Atbu.AtbuPastQuestion.controller;

import com.Atbu.AtbuPastQuestion.dao.CourseRepository;
import com.Atbu.AtbuPastQuestion.entity.Course;
import com.Atbu.AtbuPastQuestion.entity.Question;
import com.Atbu.AtbuPastQuestion.exception.ResourceNotFoundException;
import com.Atbu.AtbuPastQuestion.serviceLayer.CourseService;
import com.Atbu.AtbuPastQuestion.serviceLayer.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/question")
public class QuestionController {

    private final QuestionService questionService;
    CourseService courseService;
    CourseRepository courseRepository;

    public QuestionController(QuestionService questionService, CourseService courseService) {
        this.questionService = questionService;
    }

    // retrieve  all question from the database
    @GetMapping("/questions")
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    // retrieve  question by id from the database
    @GetMapping("/{questionId}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long questionId) {
        Optional<Question> question = questionService.getQuestionById(questionId);
        return question.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // retrieve   question by course id from the database
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Question>> getQuestionsByCourseId(@PathVariable Long courseId) {
        List<Question> questions = questionService.getQuestionsByCourseId(courseId);
        return ResponseEntity.ok(questions);
    }


    @PostMapping
    public ResponseEntity<Question> addQuestion(@RequestParam("text") String text,
                                                @RequestParam("courseId") Long courseId,
                                                @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            Question question = new Question();
            question.setText(text);
            Course course = new Course();
            course.setId(courseId);
            question.setCourse(course);

            if (file != null && !file.isEmpty()) {
                byte[] imageData = file.getBytes();
                question.setImage(imageData);
            }

            Question addedQuestion = questionService.addQuestion(question, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedQuestion);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // delete question by id from the database
    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId) {
        try {
            questionService.deleteQuestion(questionId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long questionId,
                                                   @RequestParam("text") String text,
                                                   @RequestParam("courseId") Long courseId,
                                                   @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            // Retrieve the existing question by ID
            Optional<Question> optionalQuestion = questionService.getQuestionById(questionId);
            if (optionalQuestion.isEmpty()) {
                return ResponseEntity.notFound().build(); // Question not found for given ID
            }
            Question question = optionalQuestion.get();
            question.setText(text);
            // Handle the image file (if provided)
            if (file != null && !file.isEmpty()) {
                byte[] imageData = file.getBytes();
                question.setImage(imageData);
            }
            // Save the updated question
            Question updatedQuestion = questionService.updateQuestion(question);
            return ResponseEntity.ok(updatedQuestion);
        } catch (IOException e) {
            e.printStackTrace();
            // If there's an exception, return an internal server error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
