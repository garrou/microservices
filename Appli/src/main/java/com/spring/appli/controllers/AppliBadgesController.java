package com.spring.appli.controllers;

import com.spring.appli.services.BadgesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/badges")
public class AppliBadgesController {

    @Autowired
    private BadgesService badgesService;

}
