package com.spring.statistics.clients;


import com.spring.statistics.dto.Person;
import com.spring.statistics.exceptions.PersonNotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("users")
public interface UsersClient {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/users/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Person getPerson(@PathVariable String id) throws PersonNotFoundException;
}
