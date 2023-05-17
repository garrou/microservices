package com.spring.participations.services;

import com.spring.participations.config.MapperDto;
import com.spring.participations.dto.ParticipationCreationDto;
import com.spring.participations.dto.ParticipationUpdateDto;
import com.spring.participations.dto.PresenceCreationDto;
import com.spring.participations.dto.PresenceUpdateDto;
import com.spring.participations.entities.Participation;
import com.spring.participations.entities.Presence;
import com.spring.participations.exceptions.ParticipationNotFoundException;
import com.spring.participations.exceptions.PresenceNotFoundException;
import com.spring.participations.repositories.ParticipationRepository;
import com.spring.participations.repositories.PresenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ParticipationService {

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private PresenceRepository presenceRepository;

    @Autowired
    private MapperDto mapperDto;

    public Participation createParticipation(ParticipationCreationDto participationCreationDto) {
        Participation participation = mapperDto.modelMapper().map(participationCreationDto, Participation.class);
        if (participation.getPresenceList() == null) {
            participation.setPresenceList(new ArrayList<>());
        }
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

    public Participation updateParticipation(String id, ParticipationUpdateDto participationUpdateDto) throws ParticipationNotFoundException {

        if (!id.equals(participationUpdateDto.getId())) {
            throw new IllegalArgumentException();
        }
        if (participationRepository.findById(participationUpdateDto.getId()).isEmpty()) {
            throw new ParticipationNotFoundException();
        }
        Participation course = mapperDto.modelMapper().map(participationUpdateDto, Participation.class);
        return participationRepository.save(course);
    }

    public List<Presence> getPresence(String id) throws ParticipationNotFoundException {
        Participation p = this.getParticipation(id);
        return p.getPresenceList();
    }

    public Presence createPresence(String id, PresenceCreationDto presenceDto) throws ParticipationNotFoundException {
        Participation p = this.getParticipation(id);
        Presence presence = mapperDto.modelMapper().map(presenceDto, Presence.class);
        presenceRepository.save(presence);
        List<Presence> pList = p.getPresenceList();
        pList.add(presence);
        p.setPresenceList(pList);
        participationRepository.save(p);
        return presence;
    }

    public Presence updatePresence(String id, PresenceUpdateDto presenceUpdateDto) throws ParticipationNotFoundException, PresenceNotFoundException {
        Participation p = this.getParticipation(id);
        Presence presence = mapperDto.modelMapper().map(presenceUpdateDto, Presence.class);
        List<Presence> pList = p.getPresenceList();
        boolean presenceExist = false;
        for (int i = 0; i < pList.size(); i++) {
            if (Objects.equals(pList.get(i).getId(), presence.getId())) {
                presenceExist = true;
                pList.set(i, presence);
                break;
            }
        }
        if (!presenceExist) {
            throw new PresenceNotFoundException();
        }
        p.setPresenceList(pList);
        participationRepository.save(p);
        return presence;
    }
}
