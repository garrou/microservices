package com.spring.badges.controllers;

import com.spring.badges.dto.BadgeCreationDto;
import com.spring.badges.dto.BadgeUpdateDto;
import com.spring.badges.entities.Badge;
import com.spring.badges.exceptions.BadgeNotFoundException;
import com.spring.badges.services.BadgeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/badges")
@Slf4j
public class BadgeController {
    @Autowired
    private BadgeService badgeService;

    @GetMapping
    public ResponseEntity<List<Badge>> getBadges(@RequestParam(value = "id-person", required = false) String idPerson) {
        log.info("BADGES - start : getBadges");
        List<Badge> badges = badgeService.getBadges(idPerson);
        log.info("BADGES - stop : getBadges");
        return ResponseEntity.ok(badges);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Badge> getBadge(@PathVariable String id) throws BadgeNotFoundException {
        log.info("BADGES - start : getBadge");
        Badge badge = badgeService.getBadge(id);
        log.info("BADGES - stop : getBadge");
        return ResponseEntity.ok(badge);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Badge> updateCourse(@PathVariable String id, @Valid @RequestBody BadgeUpdateDto badge)
            throws BadgeNotFoundException {
        log.info("BADGES - start : updateCourse");
        Badge updated = badgeService.updateBadge(id, badge);
        log.info("BADGES - stop : updateCourse");
        return ResponseEntity.ok(updated);
    }

    @PostMapping
    public ResponseEntity<Badge> createBadge(@Valid @RequestBody BadgeCreationDto badge) {
        log.info("BADGES - start : createBadge");
        Badge created = badgeService.createBadge(badge);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        log.info("BADGES - stop : createBadge");
        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBadge(@PathVariable String id) throws BadgeNotFoundException {
        log.info("BADGES - start : deleteBadge");
        badgeService.deleteBadge(id);
        log.info("BADGES - stop : deleteBadge");
        return ResponseEntity.ok("Deleted");
    }
}
