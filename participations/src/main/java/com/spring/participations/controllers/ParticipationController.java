package com.spring.participations.controllers;

import com.spring.participations.dto.ParticipationCreationDto;
import com.spring.participations.dto.ParticipationUpdateDto;
import com.spring.participations.dto.PresenceCreationDto;
import com.spring.participations.dto.PresenceUpdateDto;
import com.spring.participations.entities.Participation;
import com.spring.participations.entities.Presence;
import com.spring.participations.exceptions.ParticipationNotFoundException;
import com.spring.participations.exceptions.PresenceNotFoundException;
import com.spring.participations.services.ParticipationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/participations")
public class ParticipationController {

    @Autowired
    private ParticipationService participationService;

    @PostMapping
    public ResponseEntity<Participation> createParticipation(@Valid @RequestBody ParticipationCreationDto participation) {
        Participation created = participationService.createParticipation(participation);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Participation>> getParticipations(
            @RequestParam(value = "course", required = false) String courseId,
            @RequestParam(value = "badge-id", required = false) String badgeId
    ) {
        List<Participation> participations = participationService.getParticipations(courseId, badgeId);
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
            @Valid @RequestBody ParticipationUpdateDto participation
    ) throws ParticipationNotFoundException {
        Participation updated = participationService.updateParticipation(id, participation);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}/presence")
    public ResponseEntity<List<Presence>> getPresenceByParticipationId(@PathVariable String id) throws ParticipationNotFoundException {
        List<Presence> presenceList = participationService.getPresenceByParticipationId(id);
        return ResponseEntity.ok(presenceList);
    }

    @PostMapping("/{id}/presence")
    public Presence createPresenceByParticipationId(@PathVariable String id, @Valid @RequestBody PresenceCreationDto presenceCreationDto) throws ParticipationNotFoundException {
        Presence p = participationService.createPresenceByParticipationId(id, presenceCreationDto);
        return p;
    }

    @PutMapping("/{id}/presence")
    public ResponseEntity<Presence> updatePresenceByParticipationId(
            @PathVariable String id,
            @Valid @RequestBody PresenceUpdateDto presenceUpdateDto
    ) throws ParticipationNotFoundException, PresenceNotFoundException {
        Presence updated = participationService.updatePresenceByParticipationId(id, presenceUpdateDto);
        return ResponseEntity.ok(updated);
    }
}
