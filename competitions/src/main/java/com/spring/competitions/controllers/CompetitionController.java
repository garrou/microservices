package com.spring.competitions.controllers;

import com.spring.competitions.dto.CompetitionCreationDto;
import com.spring.competitions.dto.CompetitionUpdateDto;
import com.spring.competitions.entities.Competition;
import com.spring.competitions.exceptions.CompetitionNotFoundException;
import com.spring.competitions.exceptions.StudentAlreadyOnCompetitionException;
import com.spring.competitions.services.CompetitionService;
import com.spring.competitions.validators.Uuid;
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
@RequestMapping("/api/competitions")
public class CompetitionController {

    @Autowired
    private CompetitionService competitionService;

    @GetMapping
    public ResponseEntity<List<Competition>> getCompetitions(
            @RequestParam(value = "level", required = false)
            @Min(value = 0, message = "Level can't be lower than {value}")
            @Max(value = 5, message = "Level can't be greater than {value}") Integer level,
            @RequestParam(value = "teacher", required = false) @Uuid UUID teacherId,
            @RequestParam(value = "student", required = false) @Uuid UUID studentId
    ) {
        List<Competition> competitions = competitionService.getCompetitions(teacherId, studentId, level);
        return ResponseEntity.ok(competitions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Competition> getCompetition(@PathVariable String id) throws CompetitionNotFoundException {
        Competition competition = competitionService.getCompetition(id);
        return ResponseEntity.ok(competition);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<UUID>> getStudentsByCompetition(@PathVariable String id) throws CompetitionNotFoundException {
        List<UUID> students = competitionService.getStudentsByCompetition(id);
        return ResponseEntity.ok(students);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Competition> updateCompetition(
            @PathVariable String id,
            @Valid @RequestBody CompetitionUpdateDto competition
    ) throws CompetitionNotFoundException {
        Competition updated = competitionService.updateCompetition(id, competition);
        return ResponseEntity.ok(updated);
    }

    @PostMapping
    public ResponseEntity<Competition> createPerson(@Valid @RequestBody CompetitionCreationDto competition) {
        Competition created = competitionService.createCompetition(competition);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    @PostMapping("/{id}/student")
    public ResponseEntity<Competition> addStudent(
            @PathVariable String id,
            @RequestParam(value = "student", required = true) @Uuid UUID studentId
    ) throws CompetitionNotFoundException, StudentAlreadyOnCompetitionException {
        Competition updated = competitionService.addStudent(id, studentId);
        return ResponseEntity.ok(updated);
    }
}
