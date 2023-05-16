package com.spring.statistiques.clients;


import com.spring.statistiques.dto.Person;
import com.spring.statistiques.exceptions.PersonNotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("users")
public interface UsersClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/users/{id}", produces = "application/json")
    Person getPerson(@PathVariable String id) throws PersonNotFoundException;
}
