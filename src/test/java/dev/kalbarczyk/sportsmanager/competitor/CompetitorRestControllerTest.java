package dev.kalbarczyk.sportsmanager.competitor;

import dev.kalbarczyk.sportsmanager.common.enums.Discipline;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.competitor.repository.CompetitorRepository;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CompetitorRestControllerTest {
    @Autowired
    private CompetitorRepository competitorRepository;

    @Autowired
    private WebTestClient webTestClient;


    @BeforeEach
    void setUp() {
        competitorRepository.deleteAllInBatch();

        val TEST_COMPETITORS = List.of(
                Competitor.of("John", "Doe", 50000, "Slovenia", Discipline.FOOTBALL),
                Competitor.of("Jane", "Smith", 60000, "Singapore", Discipline.BASKETBALL),
                Competitor.of("Alice", "Johnson", 55000, "Turkmenistan", Discipline.VOLLEYBALL)
        );

        competitorRepository.saveAllAndFlush(TEST_COMPETITORS);
    }

    @Test
    void shouldGetAllCompetitors() {
        webTestClient.get().uri("/api/competitors")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Competitor.class)
                .value(competitors -> Assertions.assertThat(competitors).hasSize(3));
    }


    @Test
    void shouldReturnCompetitorById() {
        val id = competitorRepository.findByName("John").getId();

        webTestClient.get().uri("/api/competitors/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Competitor.class)
                .value(competitor -> {
                    Assertions.assertThat(competitor.getId()).isEqualTo(id);
                    Assertions.assertThat(competitor.getName()).isEqualTo("John");
                    Assertions.assertThat(competitor.getDiscipline()).isEqualTo(Discipline.FOOTBALL);
                });
    }

    @Test
    void shouldReturnNotFoundForNonExistentId() {
        val nonExistentId = Long.MAX_VALUE;

        webTestClient.get().uri("/api/competitors/{id}", nonExistentId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void shouldCreateCompetitor() {
        val newCompetitor = Competitor.of("Bob", "Taylor", 70000, "Australia", Discipline.BASKETBALL);

        webTestClient.post().uri("/api/competitors")
                .bodyValue(newCompetitor)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Competitor.class)
                .value(c -> {
                    Assertions.assertThat(c.getId()).isNotNull();
                    Assertions.assertThat(c.getName()).isEqualTo("Bob");
                    Assertions.assertThat(c.getSurname()).isEqualTo("Taylor");
                    Assertions.assertThat(c.getSalary()).isEqualTo(70000);
                    Assertions.assertThat(c.getCountry()).isEqualTo("Australia");
                    Assertions.assertThat(c.getDiscipline()).isEqualTo(Discipline.BASKETBALL);
                });
    }


    @Test
    void shouldDeleteCompetitor() {
        val id = competitorRepository.findByName("John").getId();

        webTestClient.delete().uri("/api/competitors/{id}", id)
                .exchange()
                .expectStatus().isNoContent();


        webTestClient.get().uri("/api/competitors/{id}", id)
                .exchange()
                .expectStatus().isNotFound();
    }


    @Test
    void shouldUpdateCompetitor() {
        val id = competitorRepository.findByName("John").getId();
        val updated = Competitor.of("John", "Doe", 99999, "United States", Discipline.BASKETBALL);

        webTestClient.put().uri("/api/competitors/{id}", id)
                .bodyValue(updated)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Competitor.class)
                .value(c -> {
                    Assertions.assertThat(c.getId()).isEqualTo(id);
                    Assertions.assertThat(c.getName()).isEqualTo("John");
                    Assertions.assertThat(c.getSurname()).isEqualTo("Doe");
                    Assertions.assertThat(c.getDiscipline()).isEqualTo(Discipline.BASKETBALL);
                    Assertions.assertThat(c.getSalary()).isEqualTo(99999);
                    Assertions.assertThat(c.getCountry()).isEqualTo("United States");
                });
    }

    @Test
    void shouldNotUpdateCompetitorWithInvalidData() {
        val id = competitorRepository.findByName("John").getId();
        val invalidCompetitor = Competitor.of("", "Doe", 99999, "United States", Discipline.BASKETBALL);

        webTestClient.put().uri("/api/competitors/{id}", id)
                .bodyValue(invalidCompetitor)
                .exchange()
                .expectStatus().isBadRequest();
    }
}
