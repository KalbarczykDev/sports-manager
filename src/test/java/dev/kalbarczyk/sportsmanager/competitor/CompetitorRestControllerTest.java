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
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CompetitorRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CompetitorRepository competitorRepository;

    private WebTestClient webTestClient;

    private final List<Long> insertedIds = new ArrayList<>();

    @BeforeEach
    void setUp() {
        this.webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:" + port + "/api/competitors").build();

        competitorRepository.deleteAll();
        competitorRepository.flush();

        insertedIds.clear();


        List<Competitor> competitors = List.of(
                Competitor.of("John", "Doe", 50000, "USA", Discipline.FOOTBALL),
                Competitor.of("Jane", "Smith", 60000, "Canada", Discipline.BASKETBALL),
                Competitor.of("Alice", "Johnson", 55000, "UK", Discipline.VOLLEYBALL)
        );


        for (Competitor c : competitors) {
            val id = Objects.requireNonNull(webTestClient.post()
                            .uri("")
                            .bodyValue(c)
                            .exchange()
                            .expectStatus().isCreated()
                            .expectBody(Competitor.class)
                            .returnResult()
                            .getResponseBody())
                    .getId();

            insertedIds.add(id);
        }

        webTestClient.get().uri("")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Competitor.class)
                .value(returnedCompetitors -> {
                    Assertions.assertThat(returnedCompetitors).hasSize(competitors.size());
                    Assertions.assertThat(returnedCompetitors.getFirst().getId()).isEqualTo(insertedIds.getFirst());
                    Assertions.assertThat(returnedCompetitors.getLast().getId()).isEqualTo(insertedIds.getLast());
                });
    }

    @Test
    void shouldReturnCompetitorById() {
        val id = insertedIds.getFirst();

        webTestClient.get().uri("/" + id)
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
    void shouldCreateCompetitor() {
        val newCompetitor = Competitor.of("Bob", "Taylor", 70000, "Australia", Discipline.BASKETBALL);

        webTestClient.post().uri("")
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
        val id = insertedIds.getFirst();

        webTestClient.delete().uri("/" + id)
                .exchange()
                .expectStatus().isNoContent();

        webTestClient.get().uri("/" + id)
                .exchange()
                .expectStatus().isNotFound();
    }


    @Test
    void shouldUpdateCompetitor() {
        val id = insertedIds.getFirst();
        val updated = Competitor.of("John", "Doe", 99999, "USA", Discipline.BASKETBALL);

        webTestClient.put().uri("/" + id)
                .bodyValue(updated)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Competitor.class)
                .value(c -> {
                    Assertions.assertThat(c.getId()).isEqualTo(id);
                    Assertions.assertThat(c.getName()).isEqualTo("John");
                    Assertions.assertThat(c.getDiscipline()).isEqualTo(Discipline.BASKETBALL);
                    Assertions.assertThat(c.getSalary()).isEqualTo(99999);
                    Assertions.assertThat(c.getCountry()).isEqualTo("USA");
                });
    }
}
