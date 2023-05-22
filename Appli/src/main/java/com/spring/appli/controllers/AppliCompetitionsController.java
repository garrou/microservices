package com.spring.appli.controllers;

import com.spring.appli.services.CompetitionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/competitions")
public class AppliCompetitionsController {

    @Autowired
    private CompetitionsService competitionsService;

}
