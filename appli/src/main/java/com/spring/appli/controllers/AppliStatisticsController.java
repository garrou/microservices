package com.spring.appli.controllers;

import com.spring.appli.dto.Competition;
import com.spring.appli.dto.CourseParticipationDto;
import com.spring.appli.dto.Person;
import com.spring.appli.enums.Role;
import com.spring.appli.exceptions.AccessDeniedException;
import com.spring.appli.exceptions.BadTokenException;
import com.spring.appli.exceptions.CourseNotFoundException;
import com.spring.appli.exceptions.ParticipationNotFoundException;
import com.spring.appli.services.StatisticsService;
import com.spring.appli.utils.TokenUtil;
import com.spring.appli.validators.Uuid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/appli/statistics")
public class AppliStatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/nbCourses")
    public ResponseEntity<Integer> getNbCourses(
            @RequestHeader("Authorization") String bearer
    ) throws BadTokenException, AccessDeniedException {
        if (!Role.PRESIDENT.equals(Role.valueOf(TokenUtil.parseToken(bearer, TokenUtil.ROLE)))) {
            throw new AccessDeniedException();
        }
        return ResponseEntity.ok(statisticsService.getNbCourses());
    }

    @GetMapping("/presence/{idCourse}")
    public ResponseEntity<List<Person>> getStudentsPresentByIdCourse(
            @PathVariable String idCourse,
            @RequestHeader("Authorization") String bearer
    ) throws ParticipationNotFoundException, AccessDeniedException, BadTokenException {
        if (!Role.PRESIDENT.equals(Role.valueOf(TokenUtil.parseToken(bearer, TokenUtil.ROLE)))) {
            throw new AccessDeniedException();
        }
        return ResponseEntity.ok(statisticsService.getStudentsPresentByIdCourse(idCourse));
    }

    @GetMapping("/nbPresence/{idCourse}")
    public ResponseEntity<Integer> getNbStudentPresentByIdCourse(
            @PathVariable String idCourse,
            @RequestHeader("Authorization") String bearer
    ) throws ParticipationNotFoundException, BadTokenException, AccessDeniedException {
        if (!Role.PRESIDENT.equals(Role.valueOf(TokenUtil.parseToken(bearer, TokenUtil.ROLE)))) {
            throw new AccessDeniedException();
        }
        return ResponseEntity.ok(statisticsService.getNbStudentPresentByIdCourse(idCourse));
    }

    @GetMapping("/courses/{studentId}")
    public ResponseEntity<List<CourseParticipationDto>> getCoursesByIdStudent(
            @PathVariable String studentId,
            @RequestHeader("Authorization") String bearer
    ) throws CourseNotFoundException, BadTokenException, AccessDeniedException {
        if (!Role.PRESIDENT.equals(Role.valueOf(TokenUtil.parseToken(bearer, TokenUtil.ROLE)))) {
            throw new AccessDeniedException();
        }
        return ResponseEntity.ok(statisticsService.getCoursesByStudentId(UUID.fromString(studentId)));
    }

    @GetMapping("/competitions")
    public ResponseEntity<List<Competition>> getCompetitions(
            @RequestParam(value = "level", required = false)
            @Min(value = 0, message = "Level can't be lower than {value}")
            @Max(value = 5, message = "Level can't be greater than {value}") Integer level,
            @RequestParam(value = "teacher", required = false) @Uuid UUID teacherId,
            @RequestParam(value = "student", required = false) @Uuid UUID studentId,
            @RequestHeader("Authorization") String bearer
    ) throws AccessDeniedException, BadTokenException {
        if (!Role.PRESIDENT.equals(Role.valueOf(TokenUtil.parseToken(bearer, TokenUtil.ROLE)))) {
            throw new AccessDeniedException();
        }
        return ResponseEntity.ok(statisticsService.getCompetitions(level, teacherId, studentId));
    }

    @GetMapping("/nbCompetitions")
    public ResponseEntity<Integer> getNbCompetitions(
            @RequestParam(value = "level", required = false)
            @Min(value = 0, message = "Level can't be lower than {value}")
            @Max(value = 5, message = "Level can't be greater than {value}") Integer level,
            @RequestParam(value = "teacher", required = false) @Uuid UUID teacherId,
            @RequestParam(value = "student", required = false) @Uuid UUID studentId,
            @RequestHeader("Authorization") String bearer
    ) throws AccessDeniedException, BadTokenException {
        if (!Role.PRESIDENT.equals(Role.valueOf(TokenUtil.parseToken(bearer, TokenUtil.ROLE)))) {
            throw new AccessDeniedException();
        }
        return ResponseEntity.ok(statisticsService.getNbCompetitions(level, teacherId, studentId));
    }
}
