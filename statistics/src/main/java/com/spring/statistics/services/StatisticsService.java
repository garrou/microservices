package com.spring.statistics.services;

import com.spring.statistics.clients.*;
import com.spring.statistics.dto.*;
import com.spring.statistics.exceptions.BadgeNotFoundException;
import com.spring.statistics.exceptions.CourseNotFoundException;
import com.spring.statistics.exceptions.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsService implements com.spring.statistics.interfaces.StatisticsService {

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
    public List<Person> getStudentsPresentByIdCourse(String idCourse) {
        List<Participation> pList = this.participationClient.getParticipations(idCourse, null);
        if(pList.isEmpty()){
            return new ArrayList<>();
        }
        Set<String> idPersons = pList.get(0)
                .getPresenceList()
                .stream()
                .filter(Presence::isPresence)
                .map(Presence::getBadgeId)
                .map(this::getPersonIdFromBadge)
                .collect(Collectors.toSet());

        return idPersons
                .stream()
                .map(this::getPersonById)
                .collect(Collectors.toList());
    }

    @Override
    public int getNbStudentPresentByIdCourse(String idCours) {
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

    private String getPersonIdFromBadge(String badgeId) {
        try {
            return this.badgesClient.getBadge(badgeId).getIdPerson();
        } catch (BadgeNotFoundException e) {
            return null;
        }
    }

    private Person getPersonById(String personId) {
        try {
            return this.usersClient.getPerson(personId);
        } catch (PersonNotFoundException e) {
            return null;
        }
    }
}
