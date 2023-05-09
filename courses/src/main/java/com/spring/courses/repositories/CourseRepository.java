package com.spring.courses.repositories;

import com.spring.courses.entities.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseRepository extends CrudRepository<Course, String> {

    List<Course> findCoursesByLevel(Integer level);

    List<Course> findCoursesByTeacherId(UUID teacherId);

    List<Course> findCoursesByTeacherIdAndLevel(UUID teacherId, Integer level);
}
