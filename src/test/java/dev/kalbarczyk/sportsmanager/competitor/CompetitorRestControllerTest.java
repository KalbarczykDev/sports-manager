package dev.kalbarczyk.sportsmanager.competitor;

import dev.kalbarczyk.sportsmanager.shared.Discipline;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class CompetitorRestControllerTest {

    @LocalServerPort
    private int port;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        this.webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();

        val competitors = List.of(
                Competitor.of("John", "Doe", 50000, "USA", Discipline.FOOTBALL),
                Competitor.of("Jane", "Smith", 60000, "Canada", Discipline.BASKETBALL),
                Competitor.of("Alice", "Johnson", 55000, "UK", Discipline.VOLLEYBALL)
        );

        webTestClient.post().uri("/api/competitors")
                .bodyValue(competitors)
                .exchange()
                .expectStatus().isOk();

        webTestClient.get().uri("/api/competitors")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Competitor.class)
                .hasSize(3);
    }

    @Test
    void shouldReturnListOfCompetitors() {
        webTestClient.get().uri("/api/competitors")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Competitor.class)
                .hasSize(3);
    }

    @Test
    void shouldReturnCompetitorById() {
        webTestClient.get().uri("/api/competitors/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Competitor.class)
                .value(competitor -> {
                    assert competitor.getId() != null;
                    assert competitor.getName().equals("John");
                    assert competitor.getDiscipline() == Discipline.FOOTBALL;
                });
    }

    @Test
    void shouldCreateCompetitor() {
        val newCompetitor = Competitor.of("Bob", "Taylor", 70000, "Australia", Discipline.BASKETBALL);

        webTestClient.post().uri("/api/competitors")
                .bodyValue(newCompetitor)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Competitor.class)
                .value(c -> {
                    assert c.getId() != null;
                    assert c.getName().equals("Bob");
                });
    }

    @Test
    void shouldDeleteCompetitor() {
        webTestClient.delete().uri("/api/competitors/1")
                .exchange()
                .expectStatus().isOk();

        webTestClient.get().uri("/api/competitors/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void shouldUpdateCompetitor() {
        val updated = Competitor.of("John", "Doe", 99999, "USA", Discipline.BASKETBALL);

        webTestClient.put().uri("/api/competitors/1")
                .bodyValue(updated)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Competitor.class)
                .value(c -> {
                    assert c.getId() != null;
                    assert c.getName().equals("John");
                    assert c.getDiscipline() == Discipline.BASKETBALL;
                    assert c.getSalary() == 99999;
                    assert c.getCountry().equals("USA");

                });

    }
}
