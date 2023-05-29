package com.spring.appli.clients;


import com.spring.appli.dto.Person;
import com.spring.appli.dto.PersonCreationDto;
import com.spring.appli.dto.PersonUpdateDto;
import com.spring.appli.exceptions.PersonNotFoundException;
import com.spring.appli.validators.Uuid;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient("users")
public interface PersonsClient {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/users/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Person getPerson(@PathVariable String id) throws PersonNotFoundException;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/users",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<List<Person>> getPersons(
            @Min(value = 0, message = "Level can't be lower than {value}")
            @Max(value = 5, message = "Level can't be greater than {value}")
            @RequestParam(value = "level", required = false) Integer level,
            @Min(value = 0, message = "Superior level can't be lower than {value}")
            @Max(value = 5, message = "Superior level can't be greater than {value}")
            @RequestParam(value = "level-sup", required = false) Integer levelSup,
            @NotBlank
            @RequestParam(value = "pseudo", required = false) String pseudo
    );


    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/users",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Person> createPerson(@Valid @RequestBody PersonCreationDto person);

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/api/users/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Person> updatePerson(
            @PathVariable @Uuid UUID id,
            @Valid @RequestBody PersonUpdateDto person
    );
}
