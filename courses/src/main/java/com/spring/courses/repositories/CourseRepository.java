package com.spring.courses.repositories;

import com.spring.courses.entities.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseRepository extends CrudRepository<Course, String> {

    List<Course> findAllByLevel(Integer level);

    List<Course> findAllByTeacherId(UUID teacherId);

    List<Course> findAllByStudentsIn(UUID studentId);

    List<Course> findAllByTeacherIdAndStudentsInAndLevel(UUID teacherId, UUID studentId, Integer level);

    List<Course> findAllByTeacherIdAndStudentsIn(UUID teacherId, UUID studentId);

    List<Course> findAllByTeacherIdAndLevel(UUID teacherId, Integer level);

    List<Course> findAllByStudentsInAndLevel(UUID studentId, Integer level);
}
