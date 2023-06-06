package com.spring.badges.services;

import com.spring.badges.config.MapperDto;
import com.spring.badges.dto.BadgeCreationDto;
import com.spring.badges.dto.BadgeUpdateDto;
import com.spring.badges.entities.Badge;
import com.spring.badges.exceptions.BadgeNotFoundException;
import com.spring.badges.repositories.BadgeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BadgeService {

    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private MapperDto mapperDto;

    public List<Badge> getBadges(String idPerson) {
        if (idPerson != null) {
            return badgeRepository.findAllByIdPerson(idPerson);
        }
        return (List<Badge>) badgeRepository.findAll();
    }


    public Badge getBadge(String id) throws BadgeNotFoundException {

        Optional<Badge> badge = badgeRepository.findById(id);
        if (badge.isEmpty()) {
            log.error("BADGES - stop KO : getBadge, BadgeNotFoundException");
            throw new BadgeNotFoundException();
        }
        return badge.get();
    }

    public Badge updateBadge(String id, BadgeUpdateDto badgeUpdateDto) throws BadgeNotFoundException {

        if (!id.equals(badgeUpdateDto.getId())) {
            log.error("BADGES - stop KO : updateBadge, IllegalArgumentException");
            throw new IllegalArgumentException();
        }
        if (badgeRepository.findById(badgeUpdateDto.getId()).isEmpty()) {
            log.error("BADGES - stop KO : updateBadge, BadgeNotFoundException");
            throw new BadgeNotFoundException();
        }
        Badge badge = mapperDto.modelMapper().map(badgeUpdateDto, Badge.class);
        return badgeRepository.save(badge);
    }

    public Badge createBadge(BadgeCreationDto badgeCreationDto) {
        Badge badge = mapperDto.modelMapper().map(badgeCreationDto, Badge.class);
        return badgeRepository.save(badge);
    }


    public void deleteBadge(String id) throws BadgeNotFoundException {
        Optional<Badge> courseOptinal = badgeRepository.findById(id);
        if(courseOptinal.isEmpty()){
            log.error("BADGES - stop KO : deleteBadge, BadgeNotFoundException");
            throw new BadgeNotFoundException();
        }
        badgeRepository.deleteById(id);

    }
}
