package com.spring.appli.clients;


import com.spring.appli.dto.Competition;
import com.spring.appli.dto.CompetitionCreationDto;
import com.spring.appli.dto.CompetitionUpdateDto;
import com.spring.appli.exceptions.CompetitionNotFoundException;
import com.spring.appli.exceptions.StudentAlreadyOnCompetitionException;
import com.spring.appli.validators.Uuid;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient("competitions")
public interface CompetitionsClient {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/competitions",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<List<Competition>> getCompetitions(
            @RequestParam(value = "level", required = false)
            @Min(value = 0, message = "Level can't be lower than {value}")
            @Max(value = 5, message = "Level can't be greater than {value}") Integer level,
            @RequestParam(value = "teacher", required = false) @Uuid UUID teacherId,
            @RequestParam(value = "student", required = false) @Uuid UUID studentId);

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/competitions/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Competition> getCompetition(@PathVariable String id) throws CompetitionNotFoundException;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/competitions/{id}/students",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<List<UUID>> getStudentsByCompetition(@PathVariable String id) throws CompetitionNotFoundException;

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/api/competitions/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Competition> updateCompetition(
            @PathVariable String id,
            @Valid @RequestBody CompetitionUpdateDto competition
    ) throws CompetitionNotFoundException;

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/competitions",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Competition> createCompetition(@Valid @RequestBody CompetitionCreationDto competition);

    @RequestMapping(method = RequestMethod.PUT,
            value = "/api/competitions/{id}/add",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Competition> addStudent(
            @Valid @PathVariable String id,
            @RequestParam(value = "student", required = true) @Uuid UUID studentId
    ) throws CompetitionNotFoundException, StudentAlreadyOnCompetitionException;
}
