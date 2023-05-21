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
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        if (participation.getPresences() == null) {
            participation.setPresences(new ArrayList<>());
        }
        return participationRepository.save(participation);
    }

    public List<Participation> getParticipations(String courseId, String badgeId) {
        if (courseId != null && badgeId != null) {
            List<Participation> participationList = participationRepository.findAllByCourseId(courseId);
            List<Presence> presenceList = presenceRepository.findAllByBadgeId(badgeId);
            return filterByPresenceList(participationList, presenceList);
        } else if (badgeId != null) {
            List<Participation> participationList = (List<Participation>) participationRepository.findAll();
            List<Presence> presenceList = presenceRepository.findAllByBadgeId(badgeId);
            return filterByPresenceList(participationList, presenceList);
        } else if (courseId != null) {
            return participationRepository.findAllByCourseId(courseId);
        }
        return (List<Participation>) participationRepository.findAll();
    }

    private static List<Participation> filterByPresenceList(
            List<Participation> participationList,
            List<Presence> presenceList
    ) {
        return participationList
                .stream()
                .filter(participation -> presenceList.stream()
                        .anyMatch(presence -> participation.getPresences().contains(presence)))
                .collect(Collectors.toList());
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

    public List<Presence> getPresenceByParticipationId(String id) throws ParticipationNotFoundException {
        Participation p = this.getParticipation(id);
        return p.getPresences();
    }

    public Presence createPresenceByParticipationId(String id, PresenceCreationDto presenceDto) throws ParticipationNotFoundException {
        Participation p = this.getParticipation(id);
        Presence presence = mapperDto.modelMapper().map(presenceDto, Presence.class);
        presenceRepository.save(presence);

        List<Presence> pList = p.getPresences();
        pList.add(presence);
        p.setPresences(pList);
        participationRepository.save(p);

        return presence;
    }

    public Presence updatePresenceByParticipationId(
            String participationId,
            PresenceUpdateDto presenceUpdateDto,
            String presenceId
    ) throws ParticipationNotFoundException, PresenceNotFoundException {

        if (!presenceUpdateDto.getId().equals(presenceId)) {
            throw new IllegalArgumentException();
        }
        Participation p = this.getParticipation(participationId);
        Presence presence = mapperDto.modelMapper().map(presenceUpdateDto, Presence.class);
        List<Presence> pList = p.getPresences();
        OptionalInt presenceIndex = IntStream
                .range(0, pList.size())
                .filter(i -> pList.get(i).getId().equals(presence.getId()))
                .findFirst();

        if (presenceIndex.isEmpty()) {
            throw new PresenceNotFoundException();
        }
        pList.set(presenceIndex.getAsInt(), presence);
        p.setPresences(pList);
        participationRepository.save(p);
        return presence;
    }
}
