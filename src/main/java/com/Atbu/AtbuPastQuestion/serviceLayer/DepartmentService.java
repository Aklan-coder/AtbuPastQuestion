package com.Atbu.AtbuPastQuestion.serviceLayer;

import com.Atbu.AtbuPastQuestion.dao.DepartmentRepository;
import com.Atbu.AtbuPastQuestion.entity.Course;
import com.Atbu.AtbuPastQuestion.entity.Department;
import com.Atbu.AtbuPastQuestion.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;


    //  Method to FIND All Department from the database
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    //  Method to FIND Department by id  from the database
    public Optional<Department> getDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId);
    }

    //  Method to SAVE Department in the database
    public Department addDepartment(Department department) {
        return departmentRepository.save(department);
    }

    //  Method to DELETE Department by id  from the database
    public void deleteDepartment(Long departmentId) {
        Department department = getDepartmentById(departmentId).orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        departmentRepository.delete(department);
    }

    //  Method to UPDATE the Department existing from the database
    public Department updateDepartment(Long departmentId, Department updatedDepartment) {
        Department existingDepartment = getDepartmentById(departmentId).orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        existingDepartment.setDepartmentName(updatedDepartment.getDepartmentName());
        return departmentRepository.save(existingDepartment);
    }
}