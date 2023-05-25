package com.spring.appli.controllers;

import com.spring.appli.dto.Course;
import com.spring.appli.dto.CourseCreationDto;
import com.spring.appli.dto.CourseUpdateDto;
import com.spring.appli.exceptions.CourseNotFoundException;
import com.spring.appli.services.CoursesService;
import com.spring.appli.validators.Uuid;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
public class AppliCoursesController {

    @Autowired
    private CoursesService coursesService;

    @GetMapping()
    public ResponseEntity<List<Course>> getCourses(
            @RequestParam(value = "level", required = false)
            @Min(value = 0, message = "Level can't be lower than {value}")
            @Max(value = 5, message = "Level can't be greater than {value}") Integer level,
            @RequestParam(value = "teacher", required = false) @Uuid UUID teacherId,
            @RequestParam(value = "student", required = false) @Uuid UUID studentId
    ) {
        List<Course> courses = coursesService.getCourses(teacherId, studentId, level);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable String id) throws CourseNotFoundException {
        Course course = coursesService.getCourse(id);
        return ResponseEntity.ok(course);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<UUID>> getStudentsByCourse(@PathVariable String id) throws CourseNotFoundException {
        List<UUID> students = coursesService.getStudentsByCourse(id);
        return ResponseEntity.ok(students);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(
            @Valid @PathVariable String id,
            @RequestBody CourseUpdateDto course
    ) throws CourseNotFoundException {
        Course updated = coursesService.updateCourse(id, course);
        return ResponseEntity.ok(updated);
    }

    @PostMapping
    public ResponseEntity<Course> createPerson(@Valid @RequestBody CourseCreationDto course) {
        Course created = coursesService.createCourse(course);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }
}
