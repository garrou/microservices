package com.spring.statistics.clients;


import com.spring.statistics.dto.Competition;
import com.spring.statistics.validators.Uuid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient("competitions")
public interface CompetitionsClient {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/competitions",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Competition> getCompetitions(
            @RequestParam(value = "level", required = false)
            @Min(value = 0, message = "Level can't be lower than {value}")
            @Max(value = 5, message = "Level can't be greater than {value}") Integer level,
            @RequestParam(value = "teacher", required = false) @Uuid UUID teacherId,
            @RequestParam(value = "student", required = false) @Uuid UUID studentId);
}
