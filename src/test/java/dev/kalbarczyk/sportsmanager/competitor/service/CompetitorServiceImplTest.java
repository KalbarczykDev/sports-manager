package dev.kalbarczyk.sportsmanager.competitor.service;

import dev.kalbarczyk.sportsmanager.coach.model.Coach;
import dev.kalbarczyk.sportsmanager.coach.service.CoachService;
import dev.kalbarczyk.sportsmanager.common.exception.CrudException;
import dev.kalbarczyk.sportsmanager.competition.model.Competition;
import dev.kalbarczyk.sportsmanager.competition.service.CompetitionService;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.competitor.repository.CompetitorRepository;
import dev.kalbarczyk.sportsmanager.person.enums.Discipline;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CompetitorServiceImplTest {
    @Mock
    private CompetitorRepository competitorRepository;
    @Mock
    private CoachService coachService;
    @Mock
    private CompetitionService competitionService;

    @InjectMocks
    private CompetitorServiceImpl competitorService;

    private Competitor competitor;
    private Coach coach;
    private Competition competition;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        competitor = Competitor.builder().id(1L).name("John").surname("Doe").country("PL").discipline(Discipline.BOXING).salary(1000.0).build();
        competition = Competition.builder().id(1L).name("Test Olympics").date(LocalDate.now()).discipline(Discipline.BOXING).build();
        coach = Coach.builder().id(2L).name("Mike").surname("Smith").country("US").discipline(Discipline.BOXING).salary(2000.0).build();
    }

    /**
     * addCompetition() Tests
     */
    @Nested
    class AddCompetitionTests {
        @Test
        void shouldAddCompetitionToCompetitor() {
            when(competitorRepository.findById(1L)).thenReturn(Optional.of(competitor));
            when(competitionService.findById(1L)).thenReturn(competition);

            competitorService.addCompetition(1L, 1L);
            assertThat(competitor.getCompetitions()).contains(competition);
            verify(competitorRepository).save(competitor);
        }

        @Test
        void shouldThrowWhenDifferentDisciplines() {
            val newCompetition = Competition.builder().id(2L).name("Test Olympics").date(LocalDate.now()).discipline(Discipline.BASEBALL).build();
            when(competitorRepository.findById(1L)).thenReturn(Optional.of(competitor));
            when(competitionService.findById(2L)).thenReturn(newCompetition);

            assertThatThrownBy(() -> competitorService.addCompetition(2L, 1L)).isInstanceOf(CrudException.RelationRequirementsException.class);

            verify(competitorRepository, never()).save(any());
        }
    }

    /**
     * removeCompetition() Tests
     */
    @Nested
    class RemoveCompetitionTests {
        @Test
        void shouldRemoveCompetitionFromCompetitor() {
            when(competitorRepository.findById(1L)).thenReturn(Optional.of(competitor));
            when(competitionService.findById(1L)).thenReturn(competition);

            competitorService.removeCompetition(1L, 1L);
            assertThat(competitor.getCompetitions()).doesNotContain(competition);
            assertThat(competition.getCompetitors()).doesNotContain(competitor);
            verify(competitorRepository).save(competitor);
        }

        @Test
        void shouldThrowWhenCompetitorNotFound() {
            when(competitorRepository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> competitorService.removeCompetition(1L, 1L)).isInstanceOf(CrudException.NotFound.class);

            verify(competitorRepository, never()).save(any());
        }

        @Test
        void shouldThrowWhenCompetitionNotFound() {
            when(competitorRepository.findById(1L)).thenReturn(Optional.of(competitor));
            when(competitionService.findById(1L)).thenThrow(new CrudException.NotFound("Competition not found"));

            assertThatThrownBy(() -> competitorService.removeCompetition(1L, 1L)).isInstanceOf(CrudException.NotFound.class);
            verify(competitorRepository, never()).save(any());
        }
    }

    /**
     * addCoach() Tests
     */
    @Nested
    class AddCoachTests {
        @Test
        void shouldAddCoachToCompetitor() {
            when(competitorRepository.findById(1L)).thenReturn(Optional.of(competitor));
            when(coachService.findById(2L)).thenReturn(coach);

            competitorService.addCoach(2L, 1L);

            assertThat(competitor.getCoaches()).contains(coach);
            verify(competitorRepository).save(competitor);
        }

        @Test
        void shouldThrowWhenDifferentDisciplines() {
            val newCoach = Coach.builder().id(3L).name("Mike").surname("Doe").country("US").discipline(Discipline.FOOTBALL).salary(2000.0).build();
            when(competitorRepository.findById(1L)).thenReturn(Optional.of(competitor));
            when(coachService.findById(3L)).thenReturn(newCoach);
            assertThatThrownBy(() -> competitorService.addCoach(3L, 1L)).isInstanceOf(CrudException.RelationRequirementsException.class);
            verify(competitorRepository, never()).save(any());
        }

        @Test
        void shouldThrowWhenCompetitorNotFound() {
            when(competitorRepository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> competitorService.addCoach(2L, 1L)).isInstanceOf(CrudException.NotFound.class);

            verify(competitorRepository, never()).save(any());
        }

        @Test
        void shouldThrowWhenCoachNotFound() {
            when(competitorRepository.findById(1L)).thenReturn(Optional.of(competitor));
            when(coachService.findById(2L)).thenThrow(new CrudException.NotFound("Coach not found"));

            assertThatThrownBy(() -> competitorService.addCoach(2L, 1L)).isInstanceOf(CrudException.NotFound.class);

            verify(competitorRepository, never()).save(any());
        }
    }

    /**
     * removeCoach() Tests
     */
    @Nested
    class RemoveCoachTests {
        @Test
        void shouldRemoveCoachFromCompetitor() {
            competitor.addCoach(coach);
            when(competitorRepository.findById(1L)).thenReturn(Optional.of(competitor));
            when(coachService.findById(2L)).thenReturn(coach);

            competitorService.removeCoach(2L, 1L);

            assertThat(competitor.getCoaches()).doesNotContain(coach);
            assertThat(coach.getCompetitors()).doesNotContain(competitor);
            verify(competitorRepository).save(competitor);
        }

        @Test
        void shouldThrowWhenCompetitorNotFound() {
            when(competitorRepository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> competitorService.removeCoach(2L, 1L)).isInstanceOf(CrudException.NotFound.class);

            verify(competitorRepository, never()).save(any());
        }

        @Test
        void shouldThrowWhenCoachNotFound() {
            when(competitorRepository.findById(1L)).thenReturn(Optional.of(competitor));
            when(coachService.findById(2L)).thenThrow(CrudException.NotFound.class);

            assertThatThrownBy(() -> competitorService.removeCoach(2L, 1L)).isInstanceOf(CrudException.NotFound.class);

            verify(competitorRepository, never()).save(any());
        }
    }

    /**
     * findAllCoaches() Tests
     */
    @Nested
    class FindAllCoachesTests {
        @Test
        void shouldFindAllCoachesForCompetitor() {
            competitor.addCoach(coach);
            when(competitorRepository.findById(1L)).thenReturn(Optional.of(competitor));

            Set<Coach> coaches = competitorService.findAllCoaches(1L);

            assertThat(coaches).containsExactly(coach);
        }

        @Test
        void shouldThrowWhenCompetitorNotFound() {
            when(competitorRepository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> competitorService.findAllCoaches(1L)).isInstanceOf(CrudException.NotFound.class);
        }
    }


    /**
     * delete() Tests
     */
    @Nested
    class DeleteTests {

        @Test
        void shouldDeleteExistingCompetitor() {
            when(competitorRepository.existsById(1L)).thenReturn(true);

            competitorService.delete(1L);

            verify(competitorRepository).deleteById(1L);
        }

        @Test
        void shouldThrowWhenCompetitorNotFound() {
            when(competitorRepository.existsById(1L)).thenReturn(false);

            assertThatThrownBy(() -> competitorService.delete(1L)).isInstanceOf(CrudException.NotImplementedEntityException.class);
        }
    }

    /*
     * update() Tests
     */
    @Nested
    class UpdateTests {

        @Test
        void shouldUpdateCompetitor() {
            when(competitorRepository.existsById(1L)).thenReturn(true);
            when(competitorRepository.save(any())).thenAnswer(i -> i.getArgument(0));

            val updated = Competitor.builder().name("Updated").surname("Doe").country("PL").discipline(Discipline.BOXING).salary(1500.0).build();

            Competitor result = competitorService.update(1L, updated);

            assertThat(result.getName()).isEqualTo("Updated");
            assertThat(result.getSalary()).isEqualTo(1500.0);
            verify(competitorRepository).save(updated);
        }

        @Test
        void shouldThrowNotFoundWhenUpdatingNonExistingEntity() {
            when(competitorRepository.existsById(1L)).thenReturn(false);
            val updated = Competitor.builder().name("Updated").surname("Doe").country("PL").discipline(Discipline.BOXING).salary(1500.0).build();

            assertThatThrownBy(() -> competitorService.update(1L, updated)).isInstanceOf(CrudException.NotFound.class).hasMessageContaining("Competitor not found");

            verify(competitorRepository, never()).save(any());
        }
    }

    /*
     * save() Tests
     */
    @Nested
    class SaveTests {

        @Test
        void shouldSaveCompetitor() {
            when(competitorRepository.save(any())).thenReturn(competitor);

            Competitor saved = competitorService.save(competitor);

            assertThat(saved).isEqualTo(competitor);
            verify(competitorRepository).save(competitor);
        }
    }

    /*
     *findById() Tests
     */
    @Nested
    class FindByIdTests {

        @Test
        void shouldFindCompetitorById() {
            when(competitorRepository.findById(1L)).thenReturn(Optional.of(competitor));

            Competitor found = competitorService.findById(1L);

            assertThat(found).isEqualTo(competitor);
        }

        @Test
        void shouldThrowWhenCompetitorNotFound() {
            when(competitorRepository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> competitorService.findById(1L)).isInstanceOf(CrudException.NotFound.class);
        }
    }
}