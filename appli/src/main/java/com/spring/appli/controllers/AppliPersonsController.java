package com.spring.appli.controllers;

import com.spring.appli.dto.Person;
import com.spring.appli.dto.PersonUpdateDto;
import com.spring.appli.enums.Role;
import com.spring.appli.exceptions.AccessDeniedException;
import com.spring.appli.exceptions.BadTokenException;
import com.spring.appli.exceptions.PersonNotFoundException;
import com.spring.appli.services.PersonsService;
import com.spring.appli.utils.TokenUtil;
import com.spring.appli.validators.Uuid;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/appli/users")
public class AppliPersonsController {

    @Autowired
    private PersonsService personService;

    @GetMapping
    public ResponseEntity<List<Person>> getPersons(
            @Min(value = 0, message = "Level can't be lower than {value}")
            @Max(value = 5, message = "Level can't be greater than {value}")
            @RequestParam(value = "level", required = false) Integer level,
            @Min(value = 0, message = "Superior level can't be lower than {value}")
            @Max(value = 5, message = "Superior level can't be greater than {value}")
            @RequestParam(value = "level-sup", required = false) Integer levelSup,
            @NotBlank
            @RequestParam(value = "pseudo", required = false) String pseudo,
            @RequestHeader("Authorization") String bearer
    ) throws BadTokenException, AccessDeniedException {
        if (Role.MEMBER.equals(Role.valueOf(TokenUtil.parseToken(bearer, TokenUtil.ROLE)))
                || Role.TEACHER.equals(Role.valueOf(TokenUtil.parseToken(bearer, TokenUtil.ROLE)))) {
            throw new AccessDeniedException();
        }
        List<Person> persons = personService.getPersons(level, levelSup, pseudo);
        return ResponseEntity.ok(persons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(
            @PathVariable @Uuid UUID id,
            @RequestHeader("Authorization") String bearer
    ) throws PersonNotFoundException, BadTokenException, AccessDeniedException {
        checkTokenAndIdMatchForRole(id, bearer);
        Person person = personService.getPerson(id);
        return ResponseEntity.ok(person);
    }

    private void checkTokenAndIdMatchForRole(@PathVariable @Uuid UUID id, @RequestHeader("Authorization") String bearer) throws BadTokenException, AccessDeniedException {
        if (Role.TEACHER.equals(Role.valueOf(TokenUtil.parseToken(bearer, TokenUtil.ROLE)))
                && !Objects.equals(String.valueOf(id), TokenUtil.parseToken(bearer, TokenUtil.ID))
                || Role.MEMBER.equals(Role.valueOf(TokenUtil.parseToken(bearer, TokenUtil.ROLE)))
                && !Objects.equals(String.valueOf(id), TokenUtil.parseToken(bearer, TokenUtil.ID))) {
            throw new AccessDeniedException();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(
            @PathVariable @Uuid UUID id,
            @Valid @RequestBody PersonUpdateDto person,
            @RequestHeader("Authorization") String bearer
    ) throws PersonNotFoundException, BadTokenException, AccessDeniedException {
        checkTokenAndIdMatchForRole(id, bearer);
        Person updated = personService.updatePerson(id, person, bearer);
        return ResponseEntity.ok(updated);
    }
}
