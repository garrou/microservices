package com.spring.statistics.interfaces;

import com.spring.statistics.dto.Competition;
import com.spring.statistics.dto.CourseParticipationDto;
import com.spring.statistics.dto.Person;
import com.spring.statistics.exceptions.CourseNotFoundException;
import com.spring.statistics.exceptions.ParticipationNotFoundException;

import java.util.List;
import java.util.UUID;

public interface StatisticsService {

    int getNbCourses();

    List<Person> getStudentsPresentByIdCourse(String idCourse) throws CourseNotFoundException, ParticipationNotFoundException;

    int getNbStudentPresentByIdCourse(String idCourse) throws ParticipationNotFoundException;

    List<CourseParticipationDto> getCoursesByStudentId(UUID studentId) throws CourseNotFoundException;

    List<Competition> getCompetitions(Integer level, UUID studentId, UUID teacherId);

    int getNbCompetitions(Integer level, UUID studentId, UUID teacherId);
}
