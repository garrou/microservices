package com.spring.appli.clients;


import com.spring.appli.dto.Badge;
import com.spring.appli.dto.BadgeCreationDto;
import com.spring.appli.dto.BadgeUpdateDto;
import com.spring.appli.exceptions.BadgeNotFoundException;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("badges")
public interface BadgesClient {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/badges/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Badge> getBadge(@PathVariable String id) throws BadgeNotFoundException;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/badges",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<List<Badge>> getBadges(@RequestParam(value = "id-person", required = false) String idPerson);


    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/api/badges/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Badge> updateBadge(
            @PathVariable String id,
            @Valid @RequestBody BadgeUpdateDto badge
    ) throws BadgeNotFoundException;

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/badges",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Badge> createBadge(@Valid @RequestBody BadgeCreationDto badge);

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/api/badges/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<String> deleteBadge(@PathVariable String id) throws BadgeNotFoundException;

}
