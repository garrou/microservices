package com.spring.badges.controllers;

import com.spring.badges.dto.BadgeCreationDto;
import com.spring.badges.dto.BadgeUpdateDto;
import com.spring.badges.entities.Badge;
import com.spring.badges.exceptions.BadgeNotFoundException;
import com.spring.badges.services.BadgeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/badges")
public class BadgeController {

    @Autowired
    private BadgeService badgeService;

    @GetMapping
    public ResponseEntity<List<Badge>> getBadges(@RequestParam(value = "idPersonne", required = false) String idPersonne) {
        List<Badge> badges = badgeService.getBadges(idPersonne);
        return ResponseEntity.ok(badges);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Badge> getBadge(@PathVariable String id) throws BadgeNotFoundException {
        Badge badge = badgeService.getBadge(id);
        return ResponseEntity.ok(badge);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Badge> updateCourse(@Valid @PathVariable String id, @RequestBody BadgeUpdateDto badge) throws BadgeNotFoundException {
        Badge updated = badgeService.updateBadge(id, badge);
        return ResponseEntity.ok(updated);
    }

    @PostMapping
    public ResponseEntity<Badge> createBadge(@Valid @RequestBody BadgeCreationDto badge) {
        Badge created = badgeService.createBadge(badge);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }
}
