package com.spring.appli.controllers;

import com.spring.appli.dto.LoginDto;
import com.spring.appli.dto.Person;
import com.spring.appli.dto.PersonCreationDto;
import com.spring.appli.exceptions.PersonNotFoundException;
import com.spring.appli.exceptions.PseudoAlreadyExistException;
import com.spring.appli.exceptions.WrongAuthentificationException;
import com.spring.appli.services.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/appli")
public class AppliLoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody LoginDto loginDto
    ) throws PersonNotFoundException, WrongAuthentificationException {
        return ResponseEntity.ok().body(loginService.login(loginDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<Person> createPerson(
            @Valid @RequestBody PersonCreationDto person) throws PseudoAlreadyExistException {
        Person created = loginService.createPerson(person);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

}
