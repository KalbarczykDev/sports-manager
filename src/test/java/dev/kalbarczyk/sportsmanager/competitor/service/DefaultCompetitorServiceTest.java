package dev.kalbarczyk.sportsmanager.competitor.service;

import dev.kalbarczyk.sportsmanager.coach.model.Coach;
import dev.kalbarczyk.sportsmanager.coach.repository.CoachRepository;
import dev.kalbarczyk.sportsmanager.common.exception.CrudException;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.competitor.repository.CompetitorRepository;
import dev.kalbarczyk.sportsmanager.person.enums.Discipline;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DefaultCompetitorServiceTest {
    @Mock
    private CompetitorRepository competitorRepository;

    @Mock
    private CoachRepository coachRepository;

    @InjectMocks
    private DefaultCompetitorService competitorService;

    private Competitor competitor;
    private Coach coach;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        competitor = Competitor.builder()
                .id(1L)
                .name("John")
                .surname("Doe")
                .country("PL")
                .discipline(Discipline.BOXING)
                .salary(1000.0)
                .build();

        coach = Coach.builder()
                .id(2L)
                .name("Mike")
                .surname("Smith")
                .country("US")
                .discipline(Discipline.BOXING)
                .salary(2000.0)
                .build();
    }

    @Test
    void shouldAddCoachToCompetitor() {
        when(competitorRepository.findById(1L)).thenReturn(Optional.of(competitor));
        when(coachRepository.findById(2L)).thenReturn(Optional.of(coach));

        competitorService.addCoach(2L, 1L);

        assertThat(competitor.getCoaches()).contains(coach);
        verify(competitorRepository).save(competitor);
    }

    @Test
    void shouldRemoveCoachFromCompetitor() {
        competitor.addCoach(coach);
        when(competitorRepository.findById(1L)).thenReturn(Optional.of(competitor));
        when(coachRepository.findById(2L)).thenReturn(Optional.of(coach));

        competitorService.removeCoach(2L, 1L);

        assertThat(competitor.getCoaches()).doesNotContain(coach);
        verify(competitorRepository).save(competitor);
    }


    @Test
    void shouldFindAllCoachesForCompetitor() {
        competitor.addCoach(coach);
        when(competitorRepository.findById(1L)).thenReturn(Optional.of(competitor));

        Set<Coach> coaches = competitorService.findAllCoaches(1L);

        assertThat(coaches).containsExactly(coach);
    }

    @Test
    void shouldThrowWhenCompetitorNotFound_addCoach() {
        when(competitorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> competitorService.addCoach(2L, 1L))
                .isInstanceOf(CrudException.InvalidEntityIdException.class)
                .hasMessageContaining("competitor not found");

        verify(competitorRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenCoachNotFound_addCoach() {
        when(competitorRepository.findById(1L)).thenReturn(Optional.of(competitor));
        when(coachRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> competitorService.addCoach(2L, 1L))
                .isInstanceOf(CrudException.InvalidEntityIdException.class)
                .hasMessageContaining("coach not found");

        verify(competitorRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenCompetitorNotFound_removeCoach() {
        when(competitorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> competitorService.removeCoach(2L, 1L))
                .isInstanceOf(CrudException.InvalidEntityIdException.class)
                .hasMessageContaining("competitor not found");

        verify(competitorRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenCoachNotFound_removeCoach() {
        when(competitorRepository.findById(1L)).thenReturn(Optional.of(competitor));
        when(coachRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> competitorService.removeCoach(2L, 1L))
                .isInstanceOf(CrudException.InvalidEntityIdException.class)
                .hasMessageContaining("coach not found");

        verify(competitorRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenCompetitorNotFound_findAllCoaches() {
        when(competitorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> competitorService.findAllCoaches(1L))
                .isInstanceOf(CrudException.InvalidEntityIdException.class)
                .hasMessageContaining("competitor not found");
    }
}
