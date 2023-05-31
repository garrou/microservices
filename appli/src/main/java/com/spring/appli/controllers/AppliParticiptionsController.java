package com.spring.appli.controllers;

import com.spring.appli.dto.*;
import com.spring.appli.exceptions.ParticipationNotFoundException;
import com.spring.appli.exceptions.PresenceNotFoundException;
import com.spring.appli.services.ParticipationsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/appli/participations")
public class AppliParticiptionsController {

    @Autowired
    private ParticipationsService participationsService;

    @PostMapping
    public ResponseEntity<Participation> createParticipation(
            @Valid @RequestBody ParticipationCreationDto participation,
            @RequestHeader("Authorization") String bearer
    ) {
        Participation created = participationsService.createParticipation(participation);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Participation>> getParticipations(
            @RequestParam(value = "course", required = false) String courseId,
            @RequestParam(value = "badge-id", required = false) String badgeId,
            @RequestHeader("Authorization") String bearer
    ) {
        List<Participation> participations = participationsService.getParticipations(courseId, badgeId);
        return ResponseEntity.ok(participations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participation> getParticipation(
            @PathVariable String id,
            @RequestHeader("Authorization") String bearer
    ) throws ParticipationNotFoundException {
        Participation participation = participationsService.getParticipation(id);
        return ResponseEntity.ok(participation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Participation> updateParticipation(
            @PathVariable String id,
            @Valid @RequestBody ParticipationUpdateDto participation,
            @RequestHeader("Authorization") String bearer
    ) throws ParticipationNotFoundException {
        Participation updated = participationsService.updateParticipation(id, participation);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}/presences")
    public ResponseEntity<List<Presence>> getPresenceByParticipationId(
            @PathVariable String id,
            @RequestHeader("Authorization") String bearer
    ) throws ParticipationNotFoundException {
        List<Presence> presenceList = participationsService.getPresenceByParticipationId(id);
        return ResponseEntity.ok(presenceList);
    }

    @PostMapping("/{id}/presences")
    public Presence createPresenceByParticipationId(
            @PathVariable String id,
            @Valid @RequestBody PresenceCreationDto presenceCreationDto,
            @RequestHeader("Authorization") String bearer
    ) throws ParticipationNotFoundException {
        Presence p = participationsService.createPresenceByParticipationId(id, presenceCreationDto);
        return p;
    }

    @PutMapping("/{participationId}/presences/{presenceId}")
    public ResponseEntity<Presence> updatePresenceByParticipationId(
            @PathVariable(name = "participationId") String id,
            @Valid @RequestBody PresenceUpdateDto presenceUpdateDto,
            @PathVariable(name = "presenceId") String presenceId,
            @RequestHeader("Authorization") String bearer
    ) throws ParticipationNotFoundException, PresenceNotFoundException {
        Presence updated = participationsService.updatePresenceByParticipationId(id, presenceUpdateDto, presenceId);
        return ResponseEntity.ok(updated);
    }
}
