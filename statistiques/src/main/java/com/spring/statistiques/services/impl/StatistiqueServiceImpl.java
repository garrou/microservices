package com.spring.statistiques.services.impl;

import com.spring.statistiques.clients.*;
import com.spring.statistiques.dto.Competition;
import com.spring.statistiques.dto.Course;
import com.spring.statistiques.dto.Participation;
import com.spring.statistiques.dto.Person;
import com.spring.statistiques.exceptions.ParticipationNotFoundException;
import com.spring.statistiques.services.StatistiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class StatistiqueServiceImpl implements StatistiqueService {

    @Autowired
    private BadgesClient badgesClient;
    @Autowired
    private CompetitionsClient competitionsClient;
    @Autowired
    private CoursesClient coursesClient;
    @Autowired
    private ParticipationsClient participationClient;
    @Autowired
    private UsersClient usersClient;

    @Override
    public int getNbCourses() {
        List<Course> courses = this.coursesClient.getCourses();
        return courses.size();
    }

    @Override
    public List<Person> getStudentsPresentByIdCourse(String idCourse) throws ParticipationNotFoundException {
        Participation p = this.participationClient.getParticipation(idCourse);
        // todo finish after participations have presence implemented
        // get idBadges where  present = true
        // get idPerson linked to idBage
        return null;
    }

    @Override
    public int getNbStudentPresentByIdCourse(String idCours) {
        return 0; // todo finish after participations have presence implemented
    }

    @Override
    public HashMap<Course, Participation> getCoursesByStudentId(UUID idStu) {
        return null; // todo finish after participations have presence implemented
    }

    @Override
    public List<Competition> getCompetitions(Integer level, UUID teacherId, UUID studentId) {
        return this.competitionsClient.getCompetitions(level, teacherId, studentId);
    }

    @Override
    public int getNbCompetitions(Integer level, UUID studentId, UUID teacherId) {
        return this.competitionsClient.getCompetitions(level, teacherId, studentId).size();
    }


}
