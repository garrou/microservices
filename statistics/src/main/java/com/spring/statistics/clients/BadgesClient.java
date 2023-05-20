package com.spring.statistics.clients;


import com.spring.statistics.dto.Badge;
import com.spring.statistics.exceptions.BadgeNotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("badges")
public interface BadgesClient {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/badges/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Badge getBadge(@PathVariable String id) throws BadgeNotFoundException;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/badges",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Badge> getBadges(@RequestParam(value = "id-person", required = false) String idPerson);
}
