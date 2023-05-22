package com.spring.appli.controllers;

import com.spring.appli.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class AppliUsersController {

    @Autowired
    private UsersService usersService;

}
