package com.spring.participations.services;

import com.spring.participations.config.MapperDto;
import com.spring.participations.dto.ParticipationEditionDto;
import com.spring.participations.entities.Participation;
import com.spring.participations.exceptions.ParticipationNotFoundException;
import com.spring.participations.repositories.ParticipationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipationService {

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private MapperDto mapperDto;

    public Participation createParticipation(ParticipationEditionDto participationCreationDto) {
        Participation participation = mapperDto.modelMapper().map(participationCreationDto, Participation.class);
        return participationRepository.save(participation);
    }

    public List<Participation> getParticipations(String courseId) {

        if (courseId != null) {
            return participationRepository.findAllByCourseId(courseId);
        }
        return (List<Participation>) participationRepository.findAll();
    }

    public Participation getParticipation(String id) throws ParticipationNotFoundException {

        Optional<Participation> participation = participationRepository.findById(id);

        if (participation.isEmpty()) {
            throw new ParticipationNotFoundException();
        }
        return participation.get();
    }

    public Participation updateParticipation(String id, ParticipationEditionDto participationEditionDto) throws ParticipationNotFoundException {

        if (!id.equals(participationEditionDto.getId())) {
            throw new IllegalArgumentException();
        }
        if (participationRepository.findById(participationEditionDto.getId()).isEmpty()) {
            throw new ParticipationNotFoundException();
        }
        Participation course = mapperDto.modelMapper().map(participationEditionDto, Participation.class);
        return participationRepository.save(course);
    }
}
