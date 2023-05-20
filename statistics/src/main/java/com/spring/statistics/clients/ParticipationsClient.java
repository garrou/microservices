package com.spring.statistics.clients;


import com.spring.statistics.dto.Participation;
import com.spring.statistics.exceptions.ParticipationNotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("participations")
public interface ParticipationsClient {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/participations/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Participation getParticipation(@PathVariable String id) throws ParticipationNotFoundException;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/participations",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Participation> getParticipations(
            @RequestParam(value = "course", required = false) String courseId,
            @RequestParam(value = "badge-id", required = false) String badgeId
    );
}
