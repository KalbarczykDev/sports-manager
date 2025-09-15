package dev.kalbarczyk.sportsmanager.competition.service;

import dev.kalbarczyk.sportsmanager.common.exception.CrudException;
import dev.kalbarczyk.sportsmanager.competition.model.Competition;
import dev.kalbarczyk.sportsmanager.competition.repository.CompetitionRepository;
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

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CompetitionServiceImplTest {
    @Mock
    private CompetitionRepository competitionRepository;

    @InjectMocks
    private CompetitionServiceImpl competitionService;

    private Competition competition;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        competition = Competition.builder().id(1L).name("Test Cup").discipline(Discipline.BOXING).date(LocalDate.now()).build();
    }

    /**
     * delete() Tests
     */
    @Nested
    class DeleteTests {

        @Test
        void shouldDeleteExistingCompetitor() {
            when(competitionRepository.existsById(1L)).thenReturn(true);

            competitionService.delete(1L);

            verify(competitionRepository).deleteById(1L);
        }

        @Test
        void shouldThrowWhenCompetitorNotFound() {
            when(competitionRepository.existsById(1L)).thenReturn(false);
            assertThatThrownBy(() -> competitionService.delete(1L)).isInstanceOf(CrudException.NotImplementedEntityException.class);
        }
    }

    /*
     * update() Tests
     */
    @Nested
    class UpdateTests {

        @Test
        void shouldUpdateCompetitor() {
            when(competitionRepository.existsById(1L)).thenReturn(true);
            when(competitionRepository.save(any())).thenAnswer(i -> i.getArgument(0));

            val localDate = LocalDate.now();
            val updated = Competition.builder().name("Updated Cup").discipline(Discipline.BOXING).date(localDate).build();
            val result = competitionService.update(1L, updated);

            assertThat(result.getName()).isEqualTo("Updated Cup");
            assertThat(result.getDate()).isEqualTo(localDate);
            verify(competitionRepository).save(updated);
        }

        @Test
        void shouldThrowNotFoundWhenUpdatingNonExistingEntity() {
            when(competitionRepository.existsById(1L)).thenReturn(false);
            val updated = Competition.builder().name("Updated Cup").discipline(Discipline.BOXING).date(LocalDate.now()).build();

            assertThatThrownBy(() -> competitionService.update(1L, updated)).isInstanceOf(CrudException.NotFound.class).hasMessageContaining("Competitor not found");

            verify(competitionRepository, never()).save(any());
        }
    }

    /*
     * save() Tests
     */
    @Nested
    class SaveTests {

        @Test
        void shouldSaveCompetitor() {
            when(competitionRepository.save(any())).thenReturn(competition);

            val saved = competitionService.save(competition);

            assertThat(saved).isEqualTo(competition);
            verify(competitionRepository).save(competition);
        }
    }

    /*
     *findById() Tests
     */
    @Nested
    class FindByIdTests {

        @Test
        void shouldFindCompetitorById() {
            when(competitionRepository.findById(1L)).thenReturn(Optional.of(competition));

            val found = competitionService.findById(1L);

            assertThat(found).isEqualTo(competition);
        }

        @Test
        void shouldThrowWhenCompetitorNotFound() {
            when(competitionRepository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> competitionService.findById(1L)).isInstanceOf(CrudException.NotFound.class);
        }
    }

}
