package com.jp.tests.api.starwars.planets.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.jp.tests.api.starwars.planets.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest // Montar o contexto de aplicação do spring, ou seja todas as classes, para injetar as dependencias nas classes de testes
@SpringBootTest(classes = PlanetService.class)
public class PlanetServiceService {

    private PlanetService planetService;

    // Padrão de nomeclatura -> operacao_estado_retornoEsperado
    @Test
    public void createPlanet_WithValidData_ReturnsPlanet(){
        // system under test
        Planet sut = planetService.create(PLANET);

        assertThat(sut).isEqualTo(PLANET);
    }

}
