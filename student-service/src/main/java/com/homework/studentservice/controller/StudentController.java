package com.homework.studentservice.controller;

import com.homework.studentservice.dto.EnrollRequest;
import com.homework.studentservice.entity.Student;
import com.homework.studentservice.repository.StudentRepository;
import com.homework.studentservice.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentRepository repo;
    private final StudentService service;

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student s) {
        s.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(s));
    }

    @GetMapping("/{id}")
    public Student get(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found: " + id));
    }

    @PostMapping("/enroll")
    public Student enroll(@Valid @RequestBody EnrollRequest request) {
        return service.enroll(request.studentId(), request.courseId());
    }
}
