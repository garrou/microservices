package com.spring.participations.controllers;

import com.spring.participations.dto.ParticipationEditionDto;
import com.spring.participations.entities.Participation;
import com.spring.participations.exceptions.ParticipationNotFoundException;
import com.spring.participations.services.ParticipationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ParticipationController {

    @Autowired
    private ParticipationService participationService;

    @PostMapping
    public ResponseEntity<Participation> createParticipation(@Valid @RequestBody ParticipationEditionDto participation) {
        Participation created = participationService.createParticipation(participation);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Participation>> getParticipations(
            @RequestParam(value = "course", required = false) String courseId
    ) {
        List<Participation> participations = participationService.getParticipations(courseId);
        return ResponseEntity.ok(participations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participation> getParticipation(@PathVariable String id) throws ParticipationNotFoundException {
        Participation participation = participationService.getParticipation(id);
        return ResponseEntity.ok(participation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Participation> updateParticipation(
            @PathVariable String id,
            @Valid @RequestBody ParticipationEditionDto participation
    ) throws ParticipationNotFoundException {
        Participation updated = participationService.updateParticipation(id, participation);
        return ResponseEntity.ok(updated);
    }
}
