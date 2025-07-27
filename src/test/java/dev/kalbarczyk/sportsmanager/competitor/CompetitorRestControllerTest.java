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
public class CompetitorRestControllerTest {

    @LocalServerPort
    private int port;

    private WebTestClient webTestClient;

    private final List<Long> insertedIds = new ArrayList<>();

    /**
     * This method runs before each test.
     * It:
     * - Initializes the WebTestClient with the current server port
     * - Creates and inserts 3 competitors using POST requests
     * - Stores their generated IDs
     * - Asserts that exactly 3 competitors exist in the database
     */
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
                            .uri("/api/competitors")
                            .bodyValue(c)
                            .exchange()
                            .expectStatus().isOk()
                            .expectBody(Competitor.class)
                            .returnResult()
                            .getResponseBody())
                    .getId();

            insertedIds.add(id);
        }


        webTestClient.get().uri("/api/competitors")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Competitor.class)
                .hasSize(3);
    }

    /**
     * Test: GET /api/competitors
     * Description: Ensures that the list of competitors contains exactly 3 entries.
     */
    @Test
    void shouldReturnListOfCompetitors() {
        webTestClient.get().uri("/api/competitors")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Competitor.class)
                .value(list -> Assertions.assertThat(list).hasSize(3));
    }

    /**
     * Test: GET /api/competitors/{id}
     * Description: Retrieves a specific competitor by ID and verifies their details.
     */
    @Test
    void shouldReturnCompetitorById() {
        val id = insertedIds.getFirst();

        webTestClient.get().uri("/api/competitors/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Competitor.class)
                .value(competitor -> {
                    Assertions.assertThat(competitor.getId()).isEqualTo(id);
                    Assertions.assertThat(competitor.getName()).isEqualTo("John");
                    Assertions.assertThat(competitor.getDiscipline()).isEqualTo(Discipline.FOOTBALL);
                });
    }

    /**
     * Test: POST /api/competitors
     * Description: Creates a new competitor and verifies the response contains a valid ID and correct data.
     */
    @Test
    void shouldCreateCompetitor() {
        val newCompetitor = Competitor.of("Bob", "Taylor", 70000, "Australia", Discipline.BASKETBALL);

        webTestClient.post().uri("/api/competitors")
                .bodyValue(newCompetitor)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Competitor.class)
                .value(c -> {
                    Assertions.assertThat(c.getId()).isNotNull();
                    Assertions.assertThat(c.getName()).isEqualTo("Bob");
                });
    }

    /**
     * Test: DELETE /api/competitors/{id}
     * Description: Deletes a competitor and verifies they no longer exist.
     */
    @Test
    void shouldDeleteCompetitor() {
        val id = insertedIds.getFirst();

        webTestClient.delete().uri("/api/competitors/" + id)
                .exchange()
                .expectStatus().isOk();

        webTestClient.get().uri("/api/competitors/" + id)
                .exchange()
                .expectStatus().isNotFound();
    }

    /**
     * Test: PUT /api/competitors/{id}
     * Description: Updates a competitor's data and verifies the updated values.
     */
    @Test
    void shouldUpdateCompetitor() {
        val id = insertedIds.getFirst();
        val updated = Competitor.of("John", "Doe", 99999, "USA", Discipline.BASKETBALL);

        webTestClient.put().uri("/api/competitors/" + id)
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
