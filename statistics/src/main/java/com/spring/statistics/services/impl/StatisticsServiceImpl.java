package com.spring.statistics.services.impl;

import com.spring.statistics.clients.*;
import com.spring.statistics.dto.*;
import com.spring.statistics.exceptions.BadgeNotFoundException;
import com.spring.statistics.exceptions.CourseNotFoundException;
import com.spring.statistics.exceptions.ParticipationNotFoundException;
import com.spring.statistics.exceptions.PersonNotFoundException;
import com.spring.statistics.services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatisticsServiceImpl implements StatisticsService {

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
