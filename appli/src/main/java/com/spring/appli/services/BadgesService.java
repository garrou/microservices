package com.spring.appli.services;

import com.spring.appli.clients.BadgesClient;
import com.spring.appli.dto.Badge;
import com.spring.appli.dto.BadgeCreationDto;
import com.spring.appli.dto.BadgeUpdateDto;
import com.spring.appli.exceptions.BadgeNotFoundException;
import feign.FeignException;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BadgesService {
    @Autowired
    private BadgesClient badgesClient;

    public List<Badge> getBadges(String idPerson) {
        return this.badgesClient.getBadges(idPerson).getBody();
    }

    public Badge getBadge(String id) throws BadgeNotFoundException {
        try {
            return this.badgesClient.getBadge(id).getBody();
        } catch (FeignException e) {
            if (e.status() == HttpStatus.SC_NOT_FOUND) {
                throw new BadgeNotFoundException();
            }
            throw e;
        }
    }

    public Badge updateBadge(String id, BadgeUpdateDto badge) throws BadgeNotFoundException {
        try {
            return this.badgesClient.updateBadge(id, badge).getBody();
        } catch (FeignException e) {
            if (e.status() == 404) {
                throw new BadgeNotFoundException();
            }
            throw e;
        }

    }

    public Badge createBadge(BadgeCreationDto badge) {
        return this.badgesClient.createBadge(badge).getBody();
    }
}
