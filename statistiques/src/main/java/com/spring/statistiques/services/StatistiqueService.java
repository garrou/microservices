package com.spring.statistiques.services;

import com.spring.statistiques.dto.Competition;
import com.spring.statistiques.dto.Course;
import com.spring.statistiques.dto.Participation;
import com.spring.statistiques.dto.Person;
import com.spring.statistiques.exceptions.CourseNotFoundException;
import com.spring.statistiques.exceptions.ParticipationNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface StatistiqueService {

    int getNbCourses();

    List<Person> getStudentsPresentByIdCourse(String idCourse) throws CourseNotFoundException, ParticipationNotFoundException;

    int getNbStudentPresentByIdCourse(String idCourse);

    HashMap<Course, Participation> getCoursesByStudentId(UUID studentId);

    List<Competition> getCompetitions(Integer level, UUID studentId, UUID teacherId);

    int getNbCompetitions(Integer level, UUID studentId, UUID teacherId);

}
