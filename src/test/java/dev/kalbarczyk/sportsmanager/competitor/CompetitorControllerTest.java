package dev.kalbarczyk.sportsmanager.competitor;

import dev.kalbarczyk.sportsmanager.common.enums.Discipline;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CompetitorControllerTest {

    @LocalServerPort
    private int port;

    private WebTestClient webTestClient;

    private final List<Long> insertedIds = new ArrayList<>();

    @BeforeEach
    void setUp() {
        this.webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();

        insertedIds.clear();

        List<Competitor> competitors = List.of(
                Competitor.of("John", "Doe", 50000, "USA", Discipline.FOOTBALL),
                Competitor.of("Jane", "Smith", 60000, "Canada", Discipline.BASKETBALL),
                Competitor.of("Alice", "Johnson", 55000, "UK", Discipline.VOLLEYBALL)
        );


        for (Competitor c : competitors) {
            val id = Objects.requireNonNull(webTestClient.post()
                            .uri("/competitors")
                            .bodyValue(c)
                            .exchange()
                            .expectStatus().isOk()
                            .expectBody(Competitor.class)
                            .returnResult()
                            .getResponseBody())
                    .getId();

            insertedIds.add(id);
        }
    }

    @Test
    void shouldReturnCompetitorById() {
        val id = insertedIds.getFirst();

        webTestClient.get().uri("/competitors/" + id)
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

        webTestClient.post().uri("/competitors")
                .bodyValue(newCompetitor)
                .exchange()
                .expectStatus().isOk()
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

        webTestClient.delete().uri("/competitors/" + id)
                .exchange()
                .expectStatus().isNoContent();

        webTestClient.get().uri("/competitors/" + id)
                .exchange()
                .expectStatus().isNotFound();
    }


    @Test
    void shouldUpdateCompetitor() {
        val id = insertedIds.getFirst();
        val updated = Competitor.of("John", "Doe", 99999, "USA", Discipline.BASKETBALL);

        webTestClient.put().uri("/competitors/" + id)
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
