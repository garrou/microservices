package com.spring.statistics.controllers;

import com.spring.statistics.dto.Competition;
import com.spring.statistics.dto.Course;
import com.spring.statistics.dto.Participation;
import com.spring.statistics.dto.Person;
import com.spring.statistics.exceptions.CourseNotFoundException;
import com.spring.statistics.exceptions.ParticipationNotFoundException;
import com.spring.statistics.services.impl.StatisticsServiceImpl;
import com.spring.statistics.validators.Uuid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsServiceImpl statisticsService;

    @GetMapping("/nbCourses")
    public ResponseEntity<Integer> getNbCourses() {
        return ResponseEntity.ok(statisticsService.getNbCourses());
    }

    @GetMapping("/presence/{idCourse}")
    public ResponseEntity<List<Person>> getStudentsPresentByIdCourse(@PathVariable String idCourse) throws ParticipationNotFoundException {
        return ResponseEntity.ok(statisticsService.getStudentsPresentByIdCourse(idCourse));
    }

    @GetMapping("/nbPresence/{idCourse}")
    public ResponseEntity<Integer> getNbStudentPresentByIdCourse(@PathVariable String idCourse) throws ParticipationNotFoundException {
        return ResponseEntity.ok(statisticsService.getNbStudentPresentByIdCourse(idCourse));
    }

    @GetMapping("/courses/{studentId}")
    public ResponseEntity<HashMap<Course, Participation>> getCoursesByIdStudent(@PathVariable String studentId) throws CourseNotFoundException {
        return ResponseEntity.ok(statisticsService.getCoursesByStudentId(UUID.fromString(studentId)));
    }

    @GetMapping("/competitions")
    public ResponseEntity<List<Competition>> getCompetitions(
            @RequestParam(value = "level", required = false)
            @Min(value = 0, message = "Level can't be lower than {value}")
            @Max(value = 5, message = "Level can't be greater than {value}") Integer level,
            @RequestParam(value = "teacher", required = false) @Uuid UUID teacherId,
            @RequestParam(value = "student", required = false) @Uuid UUID studentId
    ) {
        return ResponseEntity.ok(statisticsService.getCompetitions(level, teacherId, studentId));
    }

    @GetMapping("/nbCompetitions")
    public ResponseEntity<Integer> getNbCompetitions(
            @RequestParam(value = "level", required = false)
            @Min(value = 0, message = "Level can't be lower than {value}")
            @Max(value = 5, message = "Level can't be greater than {value}") Integer level,
            @RequestParam(value = "teacher", required = false) @Uuid UUID teacherId,
            @RequestParam(value = "student", required = false) @Uuid UUID studentId
    ) {
        return ResponseEntity.ok(statisticsService.getNbCompetitions(level, teacherId, studentId));
    }


}
