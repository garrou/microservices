package com.spring.statistiques.controllers;

import com.spring.statistiques.dto.Competition;
import com.spring.statistiques.dto.Course;
import com.spring.statistiques.dto.Participation;
import com.spring.statistiques.dto.Person;
import com.spring.statistiques.exceptions.ParticipationNotFoundException;
import com.spring.statistiques.services.impl.StatistiqueServiceImpl;
import com.spring.statistiques.validators.Uuid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/statistiques")
public class StatistiquesController {

    @Autowired
    private StatistiqueServiceImpl statistiqueService;

    @GetMapping("/nbCourses")
    public ResponseEntity<Integer> getNbCourses() {
        return ResponseEntity.ok(statistiqueService.getNbCourses());
    }

    @GetMapping("/presence/{idCourse}")
    public ResponseEntity<List<Person>> getStudentsPresentByIdCourse(@PathVariable String idCourse) throws ParticipationNotFoundException {
        return ResponseEntity.ok(statistiqueService.getStudentsPresentByIdCourse(idCourse));
    }

    @GetMapping("/nbPresence/{idCourse}")
    public ResponseEntity<Integer> getNbStudentPresentByIdCourse(@PathVariable String idCourse) {
        return ResponseEntity.ok(statistiqueService.getNbStudentPresentByIdCourse(idCourse));
    }

    @GetMapping("/courses/{studentId}")
    public ResponseEntity<HashMap<Course, Participation>> getCoursesByIdStudent(@PathVariable String studentId) {
        return ResponseEntity.ok(statistiqueService.getCoursesByStudentId(UUID.fromString(studentId)));
    }

    @GetMapping("/competition")
    public ResponseEntity<List<Competition>> getCompetitions(
            @RequestParam(value = "level", required = false)
            @Min(value = 0, message = "Level can't be lower than {value}")
            @Max(value = 5, message = "Level can't be greater than {value}") Integer level,
            @RequestParam(value = "teacher", required = false) @Uuid UUID teacherId,
            @RequestParam(value = "student", required = false) @Uuid UUID studentId
    ) {
        return ResponseEntity.ok(statistiqueService.getCompetitions(level, teacherId, studentId));
    }

    @GetMapping("/nbCompetition")
    public ResponseEntity<Integer> getNbCompetitions(
            @RequestParam(value = "level", required = false)
            @Min(value = 0, message = "Level can't be lower than {value}")
            @Max(value = 5, message = "Level can't be greater than {value}") Integer level,
            @RequestParam(value = "teacher", required = false) @Uuid UUID teacherId,
            @RequestParam(value = "student", required = false) @Uuid UUID studentId
    ) {
        return ResponseEntity.ok(statistiqueService.getNbCompetitions(level, teacherId, studentId));
    }


}
