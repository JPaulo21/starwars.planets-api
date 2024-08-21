package com.jp.tests.api.starwars.planets;

import com.jp.tests.api.starwars.planets.domain.Planet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.jp.tests.api.starwars.planets.common.PlanetConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Monta o context real do spring
public class PlanetIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void createPlanet_Returns201Created(){

    }



}
