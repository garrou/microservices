package com.spring.users.controllers;

import com.spring.users.dto.PersonCreationDto;
import com.spring.users.dto.PersonUpdateDto;
import com.spring.users.entities.Person;
import com.spring.users.exceptions.PersonNotFoundException;
import com.spring.users.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public ResponseEntity<List<Person>> getPersons(
            @RequestParam(value = "level", required = false) Integer level,
            @RequestParam(value = "pseudo", required = false) String pseudo
    ) {
        List<Person> persons = personService.getPersons(level, pseudo);
        return ResponseEntity.ok(persons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable UUID id) throws PersonNotFoundException {
        Person person = personService.getPerson(id);
        return ResponseEntity.ok(person);
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@Valid @RequestBody PersonCreationDto person) {
        Person created = personService.createPerson(person);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@Valid @PathVariable UUID id, @RequestBody PersonUpdateDto person) throws PersonNotFoundException {
        Person updated = personService.updatePerson(id, person);
        return ResponseEntity.ok(updated);
    }
}
