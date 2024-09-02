package com.jp.tests.api.starwars.planets;

import com.jp.tests.api.starwars.planets.domain.Planet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static com.jp.tests.api.starwars.planets.common.PlanetConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Monta o context real do spring
@Sql(scripts = { "/import_planets.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = { "/remove_planets.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PlanetIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void createPlanet_Returns201Created(){
        Planet sut = webTestClient
                .post()
                .uri("/planets")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(PLANET)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Planet.class)
                .returnResult().getResponseBody();

        assertThat(sut.getId()).isNotNull();
        assertThat(sut.getName()).isEqualTo(PLANET.getName());
        assertThat(sut.getClimate()).isEqualTo(PLANET.getClimate());
        assertThat(sut.getTerrain()).isEqualTo(PLANET.getTerrain());
    }

    @Test
    public void getPlanet_ReturnsPlanet() {
        Planet sut = webTestClient
                .get()
                .uri("/planets/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Planet.class)
                .returnResult().getResponseBody();

        assertThat(sut.getId()).isEqualTo(TATOOINE.getId());
        assertThat(sut.getName()).isEqualTo(TATOOINE.getName());
        assertThat(sut.getClimate()).isEqualTo(TATOOINE.getClimate());
        assertThat(sut.getTerrain()).isEqualTo(TATOOINE.getTerrain());
    }

    @Test
    public void getPlanetByName_ReturnsPlanet(){
        Planet sut = webTestClient
                .get()
                .uri("/planets/name/Tatooine")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Planet.class)
                .returnResult().getResponseBody();

        assertThat(sut.getName()).isEqualTo(TATOOINE.getName());
    }

    @Test
    public void listPlanets_ReturnsAllPlanets(){
        List<Planet> sut = webTestClient
                .get()
                .uri("/planets")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Planet.class)
                .returnResult().getResponseBody();

        assertThat(sut.size()).isEqualTo(3);
    }

    @Test
    public void listPlanets_byClimate_ReturnsPlanets(){
        List<Planet> sut = webTestClient
                .get()
                .uri("/planets?climate=temperate")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Planet.class)
                .returnResult().getResponseBody();

        assertThat(sut.size()).isEqualTo(2);
    }

    @Test
    public void listPlanets_ByTerrain_ReturnsPlanets(){
        List<Planet> sut = webTestClient
                .get()
                .uri("/planets?terrain=" + TATOOINE.getTerrain())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Planet.class)
                .returnResult().getResponseBody();

        assertThat(sut).isNotNull();
        assertThat(sut.size()).isEqualTo(1);
        assertThat(sut.get(0).getTerrain()).isEqualTo(TATOOINE.getTerrain());
    }

}
