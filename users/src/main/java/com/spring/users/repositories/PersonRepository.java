package com.spring.users.repositories;

import com.spring.users.entities.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface PersonRepository extends CrudRepository<Person, UUID> {

    List<Person> findAllByLevel(Integer level);

    List<Person> findAllByPseudo(String pseudo);

    List<Person> findAllByPseudoAndLevel(String pseudo, Integer level);

    List<Person> findAllByLevelGreaterThan(Integer level);

    List<Person> findAllByPseudoAndLevelGreaterThan(String pseudo, Integer level);
}
