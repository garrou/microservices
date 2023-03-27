package com.spring.courses.services;

import com.spring.courses.config.MapperDto;
import com.spring.courses.dto.CourseCreationDto;
import com.spring.courses.entities.Course;
import com.spring.courses.exceptions.CourseNotFoundException;
import com.spring.courses.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private MapperDto mapperDto;

    public List<Course> getCourses() {
        return (List<Course>) courseRepository.findAll();
    }

    public Course getCourse(String id) throws CourseNotFoundException {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isEmpty()) {
            throw new CourseNotFoundException();
        }
        return course.get();
    }

    public Course createCourse(CourseCreationDto courseCreationDto) {
        Course course = mapperDto.modelMapper().map(courseCreationDto, Course.class);
        return courseRepository.save(course);
    }
}
