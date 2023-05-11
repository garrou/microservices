package com.spring.badges.services;

import com.spring.badges.config.MapperDto;
import com.spring.badges.dto.BadgeCreationDto;
import com.spring.badges.dto.BadgeUpdateDto;
import com.spring.badges.entities.Badge;
import com.spring.badges.exceptions.BadgeNotFoundException;
import com.spring.badges.repositories.BadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BadgeService {

    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private MapperDto mapperDto;

    public List<Badge> getBadges() {
        return (List<Badge>) badgeRepository.findAll();
    }


    public Badge getBadge(String id) throws BadgeNotFoundException {

        Optional<Badge> badge = badgeRepository.findById(id);

        if (badge.isEmpty()) {
            throw new BadgeNotFoundException();
        }
        return badge.get();
    }

    public List<Badge> getBadgeByIdPersonne(String idPersonne) throws BadgeNotFoundException {

        List<Badge> badges = (List<Badge>) badgeRepository.findAll();

        if (badges.isEmpty()) {
            throw new BadgeNotFoundException();
        }
        List<Badge> badgesByIdPersonne = new ArrayList<>();
        for (Badge badge : badges) {
            if (badge.getIdPersonne() != null && badge.getIdPersonne().equals(idPersonne)) {
                badgesByIdPersonne.add(badge);
            }
        }
        if (badgesByIdPersonne.isEmpty()) {
            throw new BadgeNotFoundException();
        }
        return badgesByIdPersonne;
    }

    public Badge updateBadge(String id, BadgeUpdateDto badgeUpdateDto) throws BadgeNotFoundException {

        if (!id.equals(badgeUpdateDto.getId())) {
            throw new IllegalArgumentException(); // TODO Change
        }
        if (badgeRepository.findById(badgeUpdateDto.getId()).isEmpty()) {
            throw new BadgeNotFoundException();
        }
        Badge badge = mapperDto.modelMapper().map(badgeUpdateDto, Badge.class);
        return badgeRepository.save(badge);
    }

    public Badge createBadge(BadgeCreationDto badgeCreationDto) {
        Badge badge = mapperDto.modelMapper().map(badgeCreationDto, Badge.class);
        return badgeRepository.save(badge);
    }
}
