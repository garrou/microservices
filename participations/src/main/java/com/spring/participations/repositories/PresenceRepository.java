package com.spring.participations.repositories;

import com.spring.participations.entities.Presence;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresenceRepository extends CrudRepository<Presence, String> {
}
