package com.spring.statistiques.clients;


import com.spring.statistiques.dto.Participation;
import com.spring.statistiques.exceptions.ParticipationNotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("participations")
public interface ParticipationsClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/participations/{id}", produces = "application/json")
    Participation getParticipation(@PathVariable String id) throws ParticipationNotFoundException;

    @RequestMapping(method = RequestMethod.GET, value = "/api/participations", produces = "application/json")
    List<Participation> getParticipations(
            @RequestParam(value = "course", required = false) String courseId,
            @RequestParam(value = "badgeId", required = false) String badgeId
    );
}
