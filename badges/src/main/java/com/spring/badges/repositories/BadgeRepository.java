package com.spring.badges.repositories;

import com.spring.badges.entities.Badge;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BadgeRepository extends CrudRepository<Badge, String> {

    List<Badge> findAllByIdPerson(String idPerson);

}
