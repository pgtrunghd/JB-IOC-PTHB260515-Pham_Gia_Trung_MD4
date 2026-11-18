package com.homework.studentservice.client;

import com.homework.studentservice.dto.CourseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "course-service")
public interface CourseClient {
    @GetMapping("/api/courses/{courseId}")
    CourseResponse getCourse(@PathVariable("courseId") Long courseId);

    @PutMapping("/api/courses/{courseId}/decrease")
    CourseResponse decreaseCapacity(@PathVariable("courseId") Long courseId);
}
