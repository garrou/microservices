package com.spring.participations.repositories;

import com.spring.participations.entities.Participation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipationRepository extends CrudRepository<Participation, String> {

    List<Participation> findAllByCourseId(String id);
}
