package com.Atbu.AtbuPastQuestion.serviceLayer;

import com.Atbu.AtbuPastQuestion.dao.CourseRepository;
import com.Atbu.AtbuPastQuestion.dao.QuestionRepository;
import com.Atbu.AtbuPastQuestion.entity.Course;
import com.Atbu.AtbuPastQuestion.entity.Question;
import com.Atbu.AtbuPastQuestion.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, CourseRepository courseRepository) {
        this.questionRepository = questionRepository;
        this.courseRepository = courseRepository;
    }


    //  Method to FIND all  questions  from the database
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    //  Method to FIND questionById from the database
    public Optional<Question> getQuestionById(Long questionId) {
        return questionRepository.findById(questionId);
    }

    //  Method to get QuestionByCourseId
    public List<Question> getQuestionsByCourseId(Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            return questionRepository.findByCourseId(courseId);
        } else {
            return Collections.emptyList();
        }

    }


    public Question addQuestion(Question question, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            byte[] imageData = file.getBytes();
            question.setImage(imageData);
        }
        return questionRepository.save(question);
    }

    public Question updateQuestion(Question updatedQuestion) {
        // Retrieve the existing question by its ID from the updatedQuestion object
        Long questionId = updatedQuestion.getId();
        Question existingQuestion = getQuestionById(questionId).orElseThrow(() -> new ResourceNotFoundException("Question not found"));
        // Update the fields of the existing question with the values from the updated question
        existingQuestion.setText(updatedQuestion.getText());
        // Update the course ID if needed
        Course updatedCourse = updatedQuestion.getCourse();
        existingQuestion.setCourse(updatedCourse);
        // Update the image if provided
        if (updatedQuestion.getImage() != null) {
            existingQuestion.setImage(updatedQuestion.getImage());
        }
        // Save and return the updated question
        return questionRepository.save(existingQuestion);
    }
    //  Method to DELETE question  by id from the database
    public void deleteQuestion(Long questionId) {
        Question question = getQuestionById(questionId).orElseThrow(()
                -> new ResourceNotFoundException("Question not found"));
        questionRepository.delete(question);
    }

}