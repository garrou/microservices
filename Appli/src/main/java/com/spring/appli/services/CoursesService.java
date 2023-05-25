package com.spring.appli.services;

import com.spring.appli.clients.CoursesClient;
import com.spring.appli.dto.Course;
import com.spring.appli.dto.CourseCreationDto;
import com.spring.appli.dto.CourseUpdateDto;
import com.spring.appli.exceptions.CourseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CoursesService {

    @Autowired
    private CoursesClient coursesClient;

    //TODO add role control
    public List<Course> getCourses(UUID teacherId, UUID studentId, Integer level) {
        return this.coursesClient.getCourses(level, teacherId, studentId).getBody();
    }

    public Course getCourse(String id) throws CourseNotFoundException {
        return this.coursesClient.getCourse(id).getBody();
    }

    public List<UUID> getStudentsByCourse(String id) throws CourseNotFoundException {
        return this.coursesClient.getStudentsByCourse(id).getBody();
    }

    public Course updateCourse(String id, CourseUpdateDto course) throws CourseNotFoundException {
        return this.coursesClient.updateCourse(id, course).getBody();
    }

    public Course createCourse(CourseCreationDto course) {
        return this.coursesClient.createCourse(course).getBody();
    }
}
