package com.spring.appli.services;

import com.spring.appli.clients.CoursesClient;
import com.spring.appli.clients.PersonsClient;
import com.spring.appli.dto.Course;
import com.spring.appli.dto.CourseCreationDto;
import com.spring.appli.dto.CourseUpdateDto;
import com.spring.appli.dto.Person;
import com.spring.appli.enums.Role;
import com.spring.appli.exceptions.*;
import com.spring.appli.utils.TokenUtil;
import feign.FeignException;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CoursesService {

    @Autowired
    private CoursesClient coursesClient;

    @Autowired
    private PersonsClient personsClient;

    public List<Course> getCourses(UUID teacherId, UUID studentId, Integer level) {
        return this.coursesClient.getCourses(level, teacherId, studentId).getBody();
    }

    public Course getCourse(String id) throws CourseNotFoundException {
        try {
            return this.coursesClient.getCourse(id).getBody();
        } catch (FeignException e) {
            if (e.status() == HttpStatus.SC_NOT_FOUND) {
                throw new CourseNotFoundException();
            }
            throw e;
        }
    }

    public List<UUID> getStudentsByCourse(String id) throws CourseNotFoundException {
        try {
            return this.coursesClient.getStudentsByCourse(id).getBody();

        } catch (FeignException e) {
            if (e.status() == HttpStatus.SC_NOT_FOUND) {
                throw new CourseNotFoundException();
            }
            throw e;
        }
    }

    public Course updateCourse(String id, CourseUpdateDto course) throws CourseNotFoundException {
        try {
            return this.coursesClient.updateCourse(id, course).getBody();
        } catch (FeignException e) {
            if (e.status() == HttpStatus.SC_NOT_FOUND) {
                throw new CourseNotFoundException();
            }
            throw e;
        }
    }

    public Course createCourse(CourseCreationDto course, String bearer) throws TooEarlyException, BadTokenException, PersonNotFoundException, InsufficientLevelException {
        if (!isSevenDaysAfterCurrentDay(course.getTimeSlot())) {
            throw new TooEarlyException();
        }
        if (Role.TEACHER.equals(Role.valueOf(TokenUtil.parseToken(bearer, TokenUtil.ROLE)))) {
            Person person = this.personsClient.getPerson(TokenUtil.parseToken(bearer, TokenUtil.ID));
            if (person.getLevel() < course.getLevel()) {
                throw new InsufficientLevelException();
            }
        }
        return this.coursesClient.createCourse(course).getBody();
    }

    public Course addStudent(String id, UUID studentId) throws CourseNotFoundException, StudentAlreadyOnCourseException {
        try {
            return this.coursesClient.addStudent(id, studentId).getBody();
        } catch (FeignException e) {
            if (e.status() == HttpStatus.SC_CONFLICT) {
                throw new StudentAlreadyOnCourseException();
            } else if (e.status() == HttpStatus.SC_NOT_FOUND) {
                throw new CourseNotFoundException();
            }
            throw e;
        }

    }

    public boolean isSevenDaysAfterCurrentDay(Date timeSlot) {
        // Add 7 days to the current date
        Calendar sevenDaysLater = Calendar.getInstance();
        sevenDaysLater.setTime(new Date());
        sevenDaysLater.add(Calendar.DAY_OF_MONTH, 7);

        //Get the date from timeslot
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(timeSlot);

        // Compare the dates
        return currentDate.after(sevenDaysLater);
    }

    public void deleteCourse(String id) throws CourseNotFoundException {
        try{
            this.coursesClient.deleteCourse(id);
        } catch(FeignException e){
            if(e.status() == HttpStatus.SC_NOT_FOUND){
                throw new CourseNotFoundException();
            }
            throw e;
        }

    }
}
