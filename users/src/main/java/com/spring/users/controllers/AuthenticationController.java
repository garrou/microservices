package com.spring.users.controllers;

import com.spring.users.dto.LoginDto;
import com.spring.users.entities.Person;
import com.spring.users.exceptions.PersonNotFoundException;
import com.spring.users.exceptions.WrongAuthentificationException;
import com.spring.users.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private PersonService personService;

    @PostMapping("/login")
    public ResponseEntity<Person> login(@Valid @RequestBody LoginDto loginDto)
            throws PersonNotFoundException, WrongAuthentificationException {
        Person person = personService.login(loginDto);
        return ResponseEntity.ok(person);
    }
}
