package com.homework.studentservice.service;

import com.homework.studentservice.client.CourseClient;
import com.homework.studentservice.dto.CourseResponse;
import com.homework.studentservice.entity.Student;
import com.homework.studentservice.repository.StudentRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository repo;
    private final CourseClient courseClient;

    public Student enroll(Long studentId, Long courseId) {
        Student student = repo.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found: " + studentId));

        if (student.getEnrolledCourses().contains(courseId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Already enrolled");
        }

        CourseResponse course;
        try {
            course = courseClient.getCourse(courseId);
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found: " + courseId);
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Course service unavailable");
        }

        if (course.capacity() == null || course.capacity() <= 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Course is full");
        }

        try {
            courseClient.decreaseCapacity(courseId);
        } catch (FeignException.Conflict e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Course is full");
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Course service unavailable");
        }

        List<Long> updated = new ArrayList<>(student.getEnrolledCourses());
        updated.add(courseId);
        student.setEnrolledCourses(updated);
        return repo.save(student);
    }
}
