package com.homework.courseservice.repository;

import com.homework.courseservice.entity.Course;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Modifying
    @Transactional
    @Query("update Course c set c.capacity = c.capacity - 1 where c.id = :id and c.capacity > 0")
    int decreaseCapacity(@Param("id") Long id);
}
