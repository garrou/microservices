package com.spring.appli.controllers;

import com.spring.appli.dto.LoginDto;
import com.spring.appli.exceptions.PersonNotFoundException;
import com.spring.appli.exceptions.WrongAuthentificationException;
import com.spring.appli.services.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/appli/login")
public class AppliLoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<String> login(
            @Valid @RequestBody LoginDto loginDto
    ) throws PersonNotFoundException, WrongAuthentificationException {
        return ResponseEntity.ok().body(loginService.login(loginDto));
    }

}
