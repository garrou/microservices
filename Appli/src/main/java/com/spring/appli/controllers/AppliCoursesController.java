package com.spring.appli.controllers;

import com.spring.appli.services.CoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
public class AppliCoursesController {

    @Autowired
    private CoursesService coursesService;

}
