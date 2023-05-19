package com.spring.participations.repositories;

import com.spring.participations.entities.Presence;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PresenceRepository extends CrudRepository<Presence, String> {

    List<Presence> findAllByBadgeId(String id);
}
