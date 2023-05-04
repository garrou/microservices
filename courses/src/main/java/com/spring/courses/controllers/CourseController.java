package com.spring.courses.controllers;

import com.spring.courses.dto.CourseCreationDto;
import com.spring.courses.dto.CourseUpdateDto;
import com.spring.courses.entities.Course;
import com.spring.courses.exceptions.CourseNotFoundException;
import com.spring.courses.services.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Course>> getCourses() {
        List<Course> courses = courseService.getCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable String id) throws CourseNotFoundException {
        Course course = courseService.getCourse(id);
        return ResponseEntity.ok(course);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<UUID>> getStudentsByCourse(@PathVariable String id) throws CourseNotFoundException {
        List<UUID> students = courseService.getStudentsByCourse(id);
        return ResponseEntity.ok(students);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@Valid @PathVariable String id, @RequestBody CourseUpdateDto course) throws CourseNotFoundException {
        Course updated = courseService.updateCourse(id, course);
        return ResponseEntity.ok(updated);
    }

    @PostMapping
    public ResponseEntity<Course> createPerson(@Valid @RequestBody CourseCreationDto course) {
        Course created = courseService.createCourse(course);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }
}
