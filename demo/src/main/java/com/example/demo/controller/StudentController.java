package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;

import com.example.demo.model.Student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/student")
public class StudentController {
    
    private static final List<Student> STUDENTS = Arrays.asList(
        new Student(1, "Rainer"),
        new Student(2, "Evan"),
        new Student(3, "Maman")
    );

    @GetMapping(path = "{studentId}")
    public Student getStudent(@PathVariable("studentId") Integer studentId){
        return STUDENTS.stream()
            .filter(student -> studentId.equals(student.getId()))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Student "+studentId+" does not exist"));
    }
}
