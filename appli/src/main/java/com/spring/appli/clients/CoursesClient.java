package com.spring.appli.clients;


import com.spring.appli.dto.Course;
import com.spring.appli.dto.CourseCreationDto;
import com.spring.appli.dto.CourseUpdateDto;
import com.spring.appli.exceptions.CourseNotFoundException;
import com.spring.appli.exceptions.StudentAlreadyOnCourseException;
import com.spring.appli.validators.Uuid;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient("courses")
public interface CoursesClient {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/courses/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Course> getCourse(@PathVariable String id) throws CourseNotFoundException;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/courses",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<List<Course>> getCourses(
            @RequestParam(value = "level", required = false)
            @Min(value = 0, message = "Level can't be lower than {value}")
            @Max(value = 5, message = "Level can't be greater than {value}") Integer level,
            @RequestParam(value = "teacher", required = false) @Uuid UUID teacherId,
            @RequestParam(value = "student", required = false) @Uuid UUID studentId
    );

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/courses/{id}/students",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<List<UUID>> getStudentsByCourse(@PathVariable String id) throws CourseNotFoundException;

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/api/courses/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Course> updateCourse(
            @PathVariable String id,
            @Valid @RequestBody CourseUpdateDto course
    ) throws CourseNotFoundException;

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/courses",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Course> createCourse(@Valid @RequestBody CourseCreationDto course);

    @RequestMapping(method = RequestMethod.PUT,
            value = "/api/courses/{id}/add",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Course> addStudent(
            @Valid @PathVariable String id,
            @RequestParam(value = "student", required = true) @Uuid UUID studentId
    ) throws CourseNotFoundException, StudentAlreadyOnCourseException;
}
