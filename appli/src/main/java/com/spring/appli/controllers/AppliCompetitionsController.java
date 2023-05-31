package com.spring.appli.controllers;

import com.spring.appli.dto.Competition;
import com.spring.appli.dto.CompetitionCreationDto;
import com.spring.appli.dto.CompetitionUpdateDto;
import com.spring.appli.exceptions.CompetitionNotFoundException;
import com.spring.appli.services.CompetitionsService;
import com.spring.appli.validators.Uuid;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/appli/competitions")
public class AppliCompetitionsController {

    @Autowired
    private CompetitionsService competitionsService;

    @GetMapping
    public ResponseEntity<List<Competition>> getCompetitions(
            @RequestParam(value = "level", required = false)
            @Min(value = 0, message = "Level can't be lower than {value}")
            @Max(value = 5, message = "Level can't be greater than {value}") Integer level,
            @RequestParam(value = "teacher", required = false) @Uuid UUID teacherId,
            @RequestParam(value = "student", required = false) @Uuid UUID studentId,
            @RequestHeader("Authorization") String bearer
    ) {
        List<Competition> competitions = competitionsService.getCompetitions(teacherId, studentId, level);
        return ResponseEntity.ok(competitions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Competition> getCompetition(
            @PathVariable String id,
            @RequestHeader("Authorization") String bearer
    ) throws CompetitionNotFoundException {
        Competition competition = competitionsService.getCompetition(id);
        return ResponseEntity.ok(competition);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<UUID>> getStudentsByCompetition(
            @PathVariable String id,
            @RequestHeader("Authorization") String bearer
    ) throws CompetitionNotFoundException {
        List<UUID> students = competitionsService.getStudentsByCompetition(id);
        return ResponseEntity.ok(students);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Competition> updateCompetition(
            @PathVariable String id,
            @Valid @RequestBody CompetitionUpdateDto competition,
            @RequestHeader("Authorization") String bearer
    ) throws CompetitionNotFoundException {
        Competition updated = competitionsService.updateCompetition(id, competition);
        return ResponseEntity.ok(updated);
    }

    @PostMapping
    public ResponseEntity<Competition> createPerson(
            @Valid @RequestBody CompetitionCreationDto competition,
            @RequestHeader("Authorization") String bearer
    ) {
        Competition created = competitionsService.createCompetition(competition);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }
}
