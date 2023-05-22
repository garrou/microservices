package com.spring.appli.clients;


import com.spring.appli.dto.Competition;
import com.spring.appli.dto.Course;
import com.spring.appli.dto.Participation;
import com.spring.appli.dto.Person;
import com.spring.appli.exceptions.CourseNotFoundException;
import com.spring.appli.exceptions.ParticipationNotFoundException;
import com.spring.appli.validators.Uuid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@FeignClient("statistics")
public interface StatisticsClient {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/statistics/nbCourses",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Integer> getNbCourses();

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/statistics/presence/{idCourse}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<List<Person>> getStudentsPresentByIdCourse(@PathVariable String idCourse) throws ParticipationNotFoundException;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/statistics/nbPresence/{idCourse}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Integer> getNbStudentPresentByIdCourse(@PathVariable String idCourse) throws ParticipationNotFoundException;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/statistics/courses/{studentId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<HashMap<Course, Participation>> getCoursesByIdStudent(@PathVariable String studentId) throws CourseNotFoundException;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/statistics/competitions",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<List<Competition>> getCompetitions(
            @RequestParam(value = "level", required = false)
            @Min(value = 0, message = "Level can't be lower than {value}")
            @Max(value = 5, message = "Level can't be greater than {value}") Integer level,
            @RequestParam(value = "teacher", required = false) @Uuid UUID teacherId,
            @RequestParam(value = "student", required = false) @Uuid UUID studentId
    );

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/statistics/nbCompetitions",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Integer> getNbCompetitions(
            @RequestParam(value = "level", required = false)
            @Min(value = 0, message = "Level can't be lower than {value}")
            @Max(value = 5, message = "Level can't be greater than {value}") Integer level,
            @RequestParam(value = "teacher", required = false) @Uuid UUID teacherId,
            @RequestParam(value = "student", required = false) @Uuid UUID studentId
    );

}
