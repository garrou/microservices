package com.spring.appli.clients;


import com.spring.appli.dto.*;
import com.spring.appli.exceptions.ParticipationNotFoundException;
import com.spring.appli.exceptions.PresenceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("participations")
public interface ParticipationsClient {

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/participations",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Participation> createParticipation(@Valid @RequestBody ParticipationCreationDto participation);

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/participations",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<List<Participation>> getParticipations(
            @RequestParam(value = "course", required = false) String courseId,
            @RequestParam(value = "badge-id", required = false) String badgeId
    );

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/participations/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Participation> getParticipation(@PathVariable String id) throws ParticipationNotFoundException;

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/api/participations/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Participation> updateParticipation(
            @PathVariable String id,
            @Valid @RequestBody ParticipationUpdateDto participation
    ) throws ParticipationNotFoundException;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/participations/{id}/presences",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<List<Presence>> getPresenceByParticipationId(
            @PathVariable String id
    ) throws ParticipationNotFoundException;

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/participations/{id}/presences",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Presence> createPresenceByParticipationId(
            @PathVariable String id,
            @Valid @RequestBody PresenceCreationDto presenceCreationDto
    ) throws ParticipationNotFoundException;

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/api/participations/{participationId}/presences/{presenceId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Presence> updatePresenceByParticipationId(
            @PathVariable(name = "participationId") String id,
            @Valid @RequestBody PresenceUpdateDto presenceUpdateDto,
            @PathVariable(name = "presenceId") String presenceId
    ) throws ParticipationNotFoundException, PresenceNotFoundException;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{participationId}/presences/{presenceId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Presence> getPresenceByParticipationIdById(
            @PathVariable(name = "participationId") String id,
            @PathVariable(name = "presenceId") String presenceId
    ) throws ParticipationNotFoundException, PresenceNotFoundException;

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/api/participations/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<String> deleteParticipation(
            @PathVariable String id
    ) throws ParticipationNotFoundException;
}
