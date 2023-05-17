package com.spring.statistiques.services.impl;

import com.spring.statistiques.clients.*;
import com.spring.statistiques.dto.*;
import com.spring.statistiques.exceptions.BadgeNotFoundException;
import com.spring.statistiques.exceptions.CourseNotFoundException;
import com.spring.statistiques.exceptions.ParticipationNotFoundException;
import com.spring.statistiques.exceptions.PersonNotFoundException;
import com.spring.statistiques.services.StatistiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        List<Participation> pList = this.participationClient.getParticipations(idCourse, null);
        Participation p = pList.get(0);
        Set<String> idPersonList = new HashSet<>();
        List<Person> personList = new ArrayList<>();
        for (Presence presence : p.getPresenceList()) {
            if (presence.isPresence()) {
                try {
                    idPersonList.add(this.badgesClient.getBadge(presence.getBadgeId()).getIdPerson());
                } catch (BadgeNotFoundException e) {
                    // ignore ?
                }
            }
        }
        for (String idPerson : idPersonList) {
            try {
                personList.add(this.usersClient.getPerson(idPerson));
            } catch (PersonNotFoundException e) {
                // ignore ?
            }
        }
        return personList;
    }

    @Override
    public int getNbStudentPresentByIdCourse(String idCours) throws ParticipationNotFoundException {
        return this.getStudentsPresentByIdCourse(idCours).size();
    }

    @Override
    public HashMap<Course, Participation> getCoursesByStudentId(UUID idStu) throws CourseNotFoundException {
        List<Badge> badgeList = this.badgesClient.getBadges(String.valueOf(idStu));
        HashMap<Course, Participation> coursesByParticipation = new HashMap<>();
        for (Badge badge : badgeList) {
            List<Participation> participationList = this.participationClient.getParticipations(null, badge.getId());
            for (Participation participation : participationList) {
                Course course = this.coursesClient.getCourse(participation.getCourseId());
                if (!coursesByParticipation.containsKey(course)) {
                    coursesByParticipation.put(course, participation);
                }
            }
        }
        return coursesByParticipation;
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
