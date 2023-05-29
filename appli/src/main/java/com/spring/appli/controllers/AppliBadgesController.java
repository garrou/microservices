package com.spring.appli.controllers;

import com.spring.appli.dto.Badge;
import com.spring.appli.dto.BadgeCreationDto;
import com.spring.appli.dto.BadgeUpdateDto;
import com.spring.appli.exceptions.BadgeNotFoundException;
import com.spring.appli.services.BadgesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/appli/badges")
public class AppliBadgesController {

    @Autowired
    private BadgesService badgesService;

    @GetMapping
    public ResponseEntity<List<Badge>> getBadges(@RequestParam(value = "id-person", required = false) String idPerson) {
        List<Badge> badges = badgesService.getBadges(idPerson);
        return ResponseEntity.ok(badges);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Badge> getBadge(@PathVariable String id) throws BadgeNotFoundException {
        Badge badge = badgesService.getBadge(id);
        return ResponseEntity.ok(badge);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Badge> updateCourse(
            @Valid @PathVariable String id,
            @RequestBody BadgeUpdateDto badge
    ) throws BadgeNotFoundException {
        Badge updated = badgesService.updateBadge(id, badge);
        return ResponseEntity.ok(updated);
    }

    @PostMapping
    public ResponseEntity<Badge> createBadge(@Valid @RequestBody BadgeCreationDto badge) {
        Badge created = badgesService.createBadge(badge);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }
}
