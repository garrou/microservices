package com.spring.appli.services;

import com.spring.appli.clients.StatisticsClient;
import com.spring.appli.dto.Competition;
import com.spring.appli.dto.Course;
import com.spring.appli.dto.Participation;
import com.spring.appli.dto.Person;
import com.spring.appli.exceptions.CourseNotFoundException;
import com.spring.appli.exceptions.ParticipationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class StatisticsService {

    @Autowired
    private StatisticsClient statisticsClient;

    //TODO add role control
    public List<Person> getStudentsPresentByIdCourse(String idCourse) throws ParticipationNotFoundException {
        return this.statisticsClient.getStudentsPresentByIdCourse(idCourse).getBody();
    }

    public Integer getNbStudentPresentByIdCourse(String idCourse) throws ParticipationNotFoundException {
        return this.statisticsClient.getNbStudentPresentByIdCourse(idCourse).getBody();
    }

    public HashMap<Course, Participation> getCoursesByStudentId(UUID studentId) throws CourseNotFoundException {
        return this.statisticsClient.getCoursesByIdStudent(String.valueOf(studentId)).getBody();
    }

    public List<Competition> getCompetitions(Integer level, UUID teacherId, UUID studentId) {
        return this.statisticsClient.getCompetitions(level, teacherId, studentId).getBody();
    }

    public Integer getNbCompetitions(Integer level, UUID teacherId, UUID studentId) {
        return this.statisticsClient.getNbCompetitions(level, teacherId, studentId).getBody();
    }

    public Integer getNbCourses() {
        return this.statisticsClient.getNbCourses().getBody();
    }
}
