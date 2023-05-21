package com.spring.statistics.clients;


import com.spring.statistics.dto.Course;
import com.spring.statistics.exceptions.CourseNotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("courses")
public interface CoursesClient {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/courses",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Course> getCourses();

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/courses/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Course getCourse(@PathVariable String id) throws CourseNotFoundException;
}
