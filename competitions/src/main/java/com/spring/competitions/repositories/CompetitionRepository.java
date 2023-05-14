package com.spring.competitions.repositories;

import com.spring.competitions.entities.Competition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CompetitionRepository extends CrudRepository<Competition, String> {

    List<Competition> findAllByLevel(Integer level);

    List<Competition> findAllByTeacherId(UUID teacherId);

    List<Competition> findAllByStudentsIn(UUID studentId);

    List<Competition> findAllByTeacherIdAndStudentsInAndLevel(UUID teacherId, UUID studentId, Integer level);

    List<Competition> findAllByTeacherIdAndStudentsIn(UUID teacherId, UUID studentId);

    List<Competition> findAllByTeacherIdAndLevel(UUID teacherId, Integer level);

    List<Competition> findAllByStudentsInAndLevel(UUID studentId, Integer level);
}
