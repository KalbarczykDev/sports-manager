package dev.kalbarczyk.sportsmanager.coach.service;

import dev.kalbarczyk.sportsmanager.coach.model.Coach;
import dev.kalbarczyk.sportsmanager.coach.repository.CoachRepository;
import dev.kalbarczyk.sportsmanager.common.exception.CrudException;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Primary
public class DefaultCoachService implements CoachService {

    private final CoachRepository coachRepository;

    public DefaultCoachService(final CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    @Override
    public List<Coach> findAll(String sortBy, String sortDir) {
        log.info("Fetching all coaches");
        try {
            val sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
            return coachRepository.findAll(sort);
        } catch (PropertyReferenceException e) {
            throw new CrudException.InvalidSortingArgument(e.getMessage());
        }
    }

    @Override
    public Coach findById(Long id) {
        return null;
    }

    @Override
    public Coach save(Competitor competitor) {
        return null;
    }

    @Override
    public Coach update(Long id, Coach coach) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
