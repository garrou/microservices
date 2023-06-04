package com.spring.users.controllers;

import com.spring.users.dto.PersonCreationDto;
import com.spring.users.dto.PersonUpdateDto;
import com.spring.users.entities.Person;
import com.spring.users.exceptions.PersonNotFoundException;
import com.spring.users.exceptions.PseudoAlreadyExistException;
import com.spring.users.services.PersonService;
import com.spring.users.validators.Uuid;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public ResponseEntity<List<Person>> getPersons(
            @Min(value = 0, message = "Level can't be lower than {value}")
            @Max(value = 5, message = "Level can't be greater than {value}")
            @RequestParam(value = "level", required = false) Integer level,
            @Min(value = 0, message = "Superior level can't be lower than {value}")
            @Max(value = 5, message = "Superior level can't be greater than {value}")
            @RequestParam(value = "level-sup", required = false) Integer levelSup,
            @NotBlank
            @RequestParam(value = "pseudo", required = false) String pseudo
    ) {
        List<Person> persons = personService.getPersons(level, levelSup, pseudo);
        return ResponseEntity.ok(persons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable @Uuid UUID id) throws PersonNotFoundException {
        Person person = personService.getPerson(id);
        return ResponseEntity.ok(person);
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@Valid @RequestBody PersonCreationDto person) throws PseudoAlreadyExistException {
        Person created = personService.createPerson(person);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(
            @PathVariable @Uuid UUID id,
            @Valid @RequestBody PersonUpdateDto person
    ) throws PersonNotFoundException, PseudoAlreadyExistException {
        Person updated = personService.updatePerson(id, person);
        return ResponseEntity.ok(updated);
    }
}
