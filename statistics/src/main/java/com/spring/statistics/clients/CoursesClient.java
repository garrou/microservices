package com.spring.statistics.clients;


import com.spring.statistics.dto.Course;
import com.spring.statistics.exceptions.CourseNotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("courses")
public interface CoursesClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/courses", produces = "application/json")
    List<Course> getCourses();

    @RequestMapping(method = RequestMethod.GET, value = "/api/courses/{id}", produces = "application/json")
    Course getCourse(@PathVariable String id) throws CourseNotFoundException;
}
