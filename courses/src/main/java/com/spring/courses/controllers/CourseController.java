package com.spring.courses.controllers;

import com.spring.courses.dto.CourseCreationDto;
import com.spring.courses.dto.CourseUpdateDto;
import com.spring.courses.entities.Course;
import com.spring.courses.exceptions.CourseNotFoundException;
import com.spring.courses.exceptions.StudentAlreadyOnCourseException;
import com.spring.courses.services.CourseService;
import com.spring.courses.validators.Uuid;
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
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping()
    public ResponseEntity<List<Course>> getCourses(
            @RequestParam(value = "level", required = false)
            @Min(value = 0, message = "Level can't be lower than {value}")
            @Max(value = 5, message = "Level can't be greater than {value}") Integer level,
            @RequestParam(value = "teacher", required = false) @Uuid UUID teacherId,
            @RequestParam(value = "student", required = false) @Uuid UUID studentId
    ) {
        List<Course> courses = courseService.getCourses(teacherId, studentId, level);
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
    public ResponseEntity<Course> updateCourse(
            @PathVariable String id,
            @Valid @RequestBody CourseUpdateDto course
    ) throws CourseNotFoundException {
        Course updated = courseService.updateCourse(id, course);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/student")
    public ResponseEntity<Course> addStudent(
            @PathVariable String id,
            @RequestParam(value = "student", required = true) @Uuid UUID studentId
    ) throws CourseNotFoundException, StudentAlreadyOnCourseException {
        Course updated = courseService.addStudent(id, studentId);
        return ResponseEntity.ok(updated);
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@Valid @RequestBody CourseCreationDto course) {
        Course created = courseService.createCourse(course);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable String id) throws CourseNotFoundException {
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Deleted");
    }
}
