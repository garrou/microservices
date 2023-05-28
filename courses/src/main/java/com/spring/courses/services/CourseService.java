package com.spring.courses.services;

import com.spring.courses.config.MapperDto;
import com.spring.courses.dto.CourseCreationDto;
import com.spring.courses.dto.CourseUpdateDto;
import com.spring.courses.entities.Course;
import com.spring.courses.exceptions.CourseNotFoundException;
import com.spring.courses.exceptions.StudentAlreadyOnCourseException;
import com.spring.courses.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private MapperDto mapperDto;

    public List<Course> getCourses(UUID teacherId, UUID studentId, Integer level) {

        if (teacherId != null && studentId != null && level != null) { // can't be student and teacher at the same time?
            return courseRepository.findAllByTeacherIdAndStudentsInAndLevel(teacherId, studentId, level);
        } else if (teacherId != null && studentId != null) {
            return courseRepository.findAllByTeacherIdAndStudentsIn(teacherId, studentId);
        } else if (teacherId != null && level != null) {
            return courseRepository.findAllByTeacherIdAndLevel(teacherId, level);
        } else if (studentId != null && level != null) {
            return courseRepository.findAllByStudentsInAndLevel(studentId, level);
        } else if (teacherId != null) {
            return courseRepository.findAllByTeacherId(teacherId);
        } else if (studentId != null) {
            return courseRepository.findAllByStudentsIn(studentId);
        } else if (level != null) {
            return courseRepository.findAllByLevel(level);
        }
        return (List<Course>) courseRepository.findAll();
    }

    public Course getCourse(String id) throws CourseNotFoundException {

        Optional<Course> course = courseRepository.findById(id);

        if (course.isEmpty()) {
            throw new CourseNotFoundException();
        }
        return course.get();
    }

    public Course updateCourse(String id, CourseUpdateDto courseUpdateDto) throws CourseNotFoundException {

        if (!id.equals(courseUpdateDto.getId())) {
            throw new IllegalArgumentException();
        }
        if (courseRepository.findById(courseUpdateDto.getId()).isEmpty()) {
            throw new CourseNotFoundException();
        }
        Course course = mapperDto.modelMapper().map(courseUpdateDto, Course.class);
        if(course.getStudents() == null){
            course.setStudents(new ArrayList<>());
        }
        return courseRepository.save(course);
    }

    public List<UUID> getStudentsByCourse(String id) throws CourseNotFoundException {

        Optional<Course> course = courseRepository.findById(id);

        if (course.isEmpty()) {
            throw new CourseNotFoundException();
        }
        return course.get().getStudents();
    }

    public Course createCourse(CourseCreationDto courseCreationDto) {
        Course course = mapperDto.modelMapper().map(courseCreationDto, Course.class);
        if(course.getStudents() == null){
            course.setStudents(new ArrayList<>());
        }
        return courseRepository.save(course);
    }

    public Course addStudent(String id, UUID studentId) throws CourseNotFoundException, StudentAlreadyOnCourseException {
        Optional<Course> course = courseRepository.findById(id);
        if(course.isEmpty()){
            throw new CourseNotFoundException();
        }
        List<UUID> listStudent = course.get().getStudents();
        if(listStudent.contains(studentId)){
            throw new StudentAlreadyOnCourseException();
        }
        listStudent.add(studentId);
        course.get().setStudents(listStudent);
        return courseRepository.save(course.get());
    }
}
