package com.spring.appli.controllers;

import com.spring.appli.services.ParticipationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/participations")
public class AppliParticiptionsController {

    @Autowired
    private ParticipationsService participationsService;

}
