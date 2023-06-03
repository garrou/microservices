package com.spring.appli.controllers;

import com.spring.appli.dto.Course;
import com.spring.appli.dto.CourseCreationDto;
import com.spring.appli.dto.CourseUpdateDto;
import com.spring.appli.enums.Role;
import com.spring.appli.exceptions.*;
import com.spring.appli.services.CoursesService;
import com.spring.appli.utils.TokenUtil;
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
@RequestMapping("/api/appli/courses")
public class AppliCoursesController {

    @Autowired
    private CoursesService coursesService;

    @GetMapping()
    public ResponseEntity<List<Course>> getCourses(
            @RequestParam(value = "level", required = false)
            @Min(value = 0, message = "Level can't be lower than {value}")
            @Max(value = 5, message = "Level can't be greater than {value}") Integer level,
            @RequestParam(value = "teacher", required = false) @Uuid UUID teacherId,
            @RequestParam(value = "student", required = false) @Uuid UUID studentId,
            @RequestHeader("Authorization") String bearer
    ) throws AccessDeniedException, BadTokenException {
        if (!TokenUtil.contains(TokenUtil.parseToken(bearer, TokenUtil.ROLE))) {
            throw new AccessDeniedException();
        }
        List<Course> courses = coursesService.getCourses(teacherId, studentId, level);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(
            @PathVariable String id,
            @RequestHeader("Authorization") String bearer
    ) throws CourseNotFoundException, AccessDeniedException, BadTokenException {
        if (!TokenUtil.contains(TokenUtil.parseToken(bearer, TokenUtil.ROLE))) {
            throw new AccessDeniedException();
        }
        Course course = coursesService.getCourse(id);
        return ResponseEntity.ok(course);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<UUID>> getStudentsByCourse(
            @PathVariable String id,
            @RequestHeader("Authorization") String bearer
    ) throws CourseNotFoundException, BadTokenException, AccessDeniedException {
        if (Role.MEMBER.equals(Role.valueOf(TokenUtil.parseToken(bearer, TokenUtil.ROLE)))) {
            throw new AccessDeniedException();
        }
        List<UUID> students = coursesService.getStudentsByCourse(id);
        return ResponseEntity.ok(students);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable String id,
            @Valid @RequestBody CourseUpdateDto course,
            @RequestHeader("Authorization") String bearer
    ) throws CourseNotFoundException, BadTokenException, AccessDeniedException {
        if (Role.MEMBER.equals(Role.valueOf(TokenUtil.parseToken(bearer, TokenUtil.ROLE))) ||
                Role.SECRETARY.equals(Role.valueOf(TokenUtil.parseToken(bearer, TokenUtil.ROLE)))) {
            throw new AccessDeniedException();
        }
        Course updated = coursesService.updateCourse(id, course);
        return ResponseEntity.ok(updated);
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(
            @Valid @RequestBody CourseCreationDto course,
            @RequestHeader("Authorization") String bearer
    ) throws AccessDeniedException, BadTokenException, TooEarlyException, PersonNotFoundException, InsufficientLevelException {
        if (Role.MEMBER.equals(Role.valueOf(TokenUtil.parseToken(bearer, TokenUtil.ROLE))) ||
                Role.SECRETARY.equals(Role.valueOf(TokenUtil.parseToken(bearer, TokenUtil.ROLE)))) {
            throw new AccessDeniedException();
        }
        Course created = coursesService.createCourse(course, bearer);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}/add")
    public ResponseEntity<Course> addStudent(
            @Valid @PathVariable String id,
            @RequestParam(value = "student", required = true) @Uuid UUID studentId,
            @RequestHeader("Authorization") String bearer
    ) throws CourseNotFoundException, StudentAlreadyOnCourseException, AccessDeniedException, BadTokenException {
        if (!TokenUtil.contains(TokenUtil.parseToken(bearer, TokenUtil.ROLE))) {
            throw new AccessDeniedException();
        }
        Course updated = coursesService.addStudent(id, studentId);
        return ResponseEntity.ok(updated);
    }

}
