package com.spring.users.repositories;

import com.spring.users.entities.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PersonRepository extends CrudRepository<Person, UUID> {

    List<Person> findPersonsByLevel(Integer level);

    List<Person> findPersonsByPseudo(String pseudo);

    List<Person> findPersonsByPseudoAndLevel(String pseudo, Integer level);
}
