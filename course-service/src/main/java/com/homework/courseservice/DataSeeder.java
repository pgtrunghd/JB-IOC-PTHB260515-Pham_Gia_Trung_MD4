package com.homework.courseservice;

import com.homework.courseservice.entity.Course;
import com.homework.courseservice.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final CourseRepository repo;

    @Override
    public void run(String... arg) {
        if (repo.count() == 0) {
            repo.save(new Course(null, "Java Core", 30, 3));
            repo.save(new Course(null, "Spring Boot", 2, 4));
            repo.save(new Course(null, "Microservices", 1, 4));
        }
    }
}
