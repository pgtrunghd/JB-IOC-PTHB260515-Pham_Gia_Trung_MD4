package com.homework.courseservice.controller;

import com.homework.courseservice.entity.Course;
import com.homework.courseservice.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseRepository repo;

    @GetMapping("/{courseId}")
    public Course getCourse(@PathVariable Long courseId) {
        return repo.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Course not found: " + courseId
                ));
    }

    @PutMapping("/{courseId}/decrease")
    public Course decrease(@PathVariable Long courseId) {
        getCourse(courseId);
        if (repo.decreaseCapacity(courseId) == 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Course is full");
        }
        return getCourse(courseId);
    }
}
