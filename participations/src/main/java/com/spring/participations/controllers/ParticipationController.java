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

    @GetMapping("/{id}/presences")
    public ResponseEntity<List<Presence>> getPresenceByParticipationId(
            @PathVariable String id
    ) throws ParticipationNotFoundException {
        List<Presence> presenceList = participationService.getPresenceByParticipationId(id);
        return ResponseEntity.ok(presenceList);
    }

    @GetMapping("/{participationId}/presences/{presenceId}")
    public ResponseEntity<Presence> getPresenceByParticipationIdById(
            @PathVariable(name = "participationId") String id,
            @PathVariable(name = "presenceId") String presenceId
    ) throws ParticipationNotFoundException, PresenceNotFoundException {
        Presence presence = participationService.getPresenceByParticipationIdById(id, presenceId);
        return ResponseEntity.ok(presence);
    }

    @PostMapping("/{id}/presences")
    public ResponseEntity<Presence> createPresenceByParticipationId(
            @PathVariable String id,
            @Valid @RequestBody PresenceCreationDto presenceCreationDto
    ) throws ParticipationNotFoundException {
        Presence created = participationService.createPresenceByParticipationId(id, presenceCreationDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{participationId}/presences/{presenceId}")
    public ResponseEntity<Presence> updatePresenceByParticipationId(
            @PathVariable(name = "participationId") String id,
            @Valid @RequestBody PresenceUpdateDto presenceUpdateDto,
            @PathVariable(name = "presenceId") String presenceId
    ) throws ParticipationNotFoundException, PresenceNotFoundException {
        Presence updated = participationService.updatePresenceByParticipationId(id, presenceUpdateDto, presenceId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteParticipation(@PathVariable String id) throws ParticipationNotFoundException {
        participationService.deleteParticipation(id);
        return ResponseEntity.ok("Deleted");
    }
}
