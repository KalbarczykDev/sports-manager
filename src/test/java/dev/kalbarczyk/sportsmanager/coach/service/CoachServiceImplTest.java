package dev.kalbarczyk.sportsmanager.coach.service;

import dev.kalbarczyk.sportsmanager.coach.model.Coach;
import dev.kalbarczyk.sportsmanager.coach.repository.CoachRepository;
import dev.kalbarczyk.sportsmanager.common.exception.CrudException;
import dev.kalbarczyk.sportsmanager.person.enums.Discipline;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public final class CoachServiceImplTest {

    @Mock
    private CoachRepository coachRepository;

    @InjectMocks
    private CoachServiceImpl coachService;

    private Coach coach;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        coach = Coach.builder()
                .id(1L)
                .name("Mike")
                .surname("Smith")
                .country("US")
                .discipline(Discipline.BOXING)
                .salary(2000.0)
                .build();
    }


    /**
     * findAll() Tests
     */
    @Nested
    class FindAllTests {
        @Test
        void shouldReturnAllCoaches() {
            when(coachRepository.findAll()).thenReturn(List.of(coach));

            val result = coachService.findAll();

            assertThat(result).containsExactly(coach);
            verify(coachRepository).findAll();
        }

        @Test
        void shouldReturnEmptyListWhenNoCoaches() {
            when(coachRepository.findAll()).thenReturn(List.of());

            val result = coachService.findAll();

            assertThat(result).isEmpty();
        }
    }

    /**
     * save() Tests
     */
    @Nested
    class SaveTests {
        @Test
        void shouldSaveCoach() {
            when(coachRepository.save(any())).thenReturn(coach);

            val saved = coachService.save(coach);

            assertThat(saved).isEqualTo(coach);
            verify(coachRepository).save(coach);
        }
    }

    /**
     * update() Tests
     */
    @Nested
    class UpdateTests {
        @Test
        void shouldUpdateCoach() {
            when(coachRepository.existsById(1L)).thenReturn(true);
            when(coachRepository.save(any())).thenAnswer(i -> i.getArgument(0));

            val updated = Coach.builder()
                    .name("Updated")
                    .surname("Smith")
                    .country("US")
                    .discipline(Discipline.BOXING)
                    .salary(2500.0)
                    .build();

            val result = coachService.update(1L, updated);

            assertThat(result.getName()).isEqualTo("Updated");
            assertThat(result.getSalary()).isEqualTo(2500.0);
            verify(coachRepository).save(updated);
        }

        @Test
        void shouldThrowNotFoundWhenUpdatingNonExistingCoach() {
            when(coachRepository.existsById(1L)).thenReturn(false);
            val updated = Coach.builder()
                    .name("Updated")
                    .surname("Smith")
                    .country("US")
                    .discipline(Discipline.BOXING)
                    .salary(2500.0)
                    .build();

            assertThatThrownBy(() -> coachService.update(1L, updated))
                    .isInstanceOf(CrudException.NotFound.class);
            verify(coachRepository, never()).save(any());
        }
    }

    /**
     * findById() Tests
     */
    @Nested
    class FindByIdTests {
        @Test
        void shouldFindCoachById() {
            when(coachRepository.findById(1L)).thenReturn(Optional.of(coach));

            val found = coachService.findById(1L);

            assertThat(found).isEqualTo(coach);
        }

        @Test
        void shouldThrowWhenCoachNotFound() {
            when(coachRepository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> coachService.findById(1L))
                    .isInstanceOf(CrudException.NotFound.class);
        }
    }

    /**
     * delete() Tests
     */
    @Nested
    class DeleteTests {
        @Test
        void shouldDeleteExistingCoach() {
            when(coachRepository.existsById(1L)).thenReturn(true);

            coachService.delete(1L);

            verify(coachRepository).deleteById(1L);
        }

        @Test
        void shouldThrowWhenCoachNotFound() {
            when(coachRepository.existsById(1L)).thenReturn(false);

            assertThatThrownBy(() -> coachService.delete(1L))
                    .isInstanceOf(CrudException.NotImplementedEntityException.class);
        }
    }
}
