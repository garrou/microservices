package com.spring.appli.controllers;

import com.spring.appli.dto.Person;
import com.spring.appli.dto.PersonCreationDto;
import com.spring.appli.dto.PersonUpdateDto;
import com.spring.appli.exceptions.PersonNotFoundException;
import com.spring.appli.services.PersonsService;
import com.spring.appli.validators.Uuid;
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
    public ResponseEntity<Person> createPerson(@Valid @RequestBody PersonCreationDto person) {
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
    ) throws PersonNotFoundException {
        Person updated = personService.updatePerson(id, person);
        return ResponseEntity.ok(updated);
    }
}
