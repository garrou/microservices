package com.spring.competitions.services;

import com.spring.competitions.config.MapperDto;
import com.spring.competitions.dto.CompetitionCreationDto;
import com.spring.competitions.dto.CompetitionUpdateDto;
import com.spring.competitions.entities.Competition;
import com.spring.competitions.exceptions.CompetitionNotFoundException;
import com.spring.competitions.repositories.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CompetitionService {

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private MapperDto mapperDto;

    public List<Competition> getCompetitions(UUID teacherId, UUID studentId, Integer level) {

        if (teacherId != null && studentId != null && level != null) {
            return competitionRepository.findAllByTeacherIdAndStudentsInAndLevel(teacherId, studentId, level);
        } else if (teacherId != null && studentId != null) {
            return competitionRepository.findAllByTeacherIdAndStudentsIn(teacherId, studentId);
        } else if (teacherId != null && level != null) {
            return competitionRepository.findAllByTeacherIdAndLevel(teacherId, level);
        } else if (studentId != null && level != null) {
            return competitionRepository.findAllByStudentsInAndLevel(studentId, level);
        } else if (teacherId != null) {
            return competitionRepository.findAllByTeacherId(teacherId);
        } else if (studentId != null) {
            return competitionRepository.findAllByStudentsIn(studentId);
        } else if (level != null) {
            return competitionRepository.findAllByLevel(level);
        }
        return (List<Competition>) competitionRepository.findAll();
    }

    public Competition getCompetition(String id) throws CompetitionNotFoundException {

        Optional<Competition> competition = competitionRepository.findById(id);

        if (competition.isEmpty()) {
            throw new CompetitionNotFoundException();
        }
        return competition.get();
    }

    public Competition updateCompetition(String id, CompetitionUpdateDto competitionUpdateDto) throws CompetitionNotFoundException {

        if (!id.equals(competitionUpdateDto.getId())) {
            throw new IllegalArgumentException();
        }
        if (competitionRepository.findById(competitionUpdateDto.getId()).isEmpty()) {
            throw new CompetitionNotFoundException();
        }
        Competition competition = mapperDto.modelMapper().map(competitionUpdateDto, Competition.class);
        return competitionRepository.save(competition);
    }

    public List<UUID> getStudentsByCompetition(String id) throws CompetitionNotFoundException {

        Optional<Competition> competition = competitionRepository.findById(id);

        if (competition.isEmpty()) {
            throw new CompetitionNotFoundException();
        }
        return competition.get().getStudents();
    }

    public Competition createCompetition(CompetitionCreationDto competitionCreationDto) {
        Competition competition = mapperDto.modelMapper().map(competitionCreationDto, Competition.class);
        return competitionRepository.save(competition);
    }
}
