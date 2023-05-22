package com.spring.appli.controllers;

import com.spring.appli.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class AppliLoginController {

    @Autowired
    private LoginService loginService;

}
