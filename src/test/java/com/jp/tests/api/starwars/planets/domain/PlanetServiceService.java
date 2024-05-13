package com.jp.tests.api.starwars.planets.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.jp.tests.api.starwars.planets.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

//@SpringBootTest // Montar o contexto de aplicação do spring, ou seja todas as classes, para injetar as dependencias nas classes de testes
@SpringBootTest(classes = PlanetService.class) // Monta o contexto especifico para o PlanetService
public class PlanetServiceService {

    @Autowired
    private PlanetService planetService;

    @MockBean
    private PlanetRepository planetRepository;

    // Padrão de nomeclatura -> operacao_estado_retornoEsperado
    @Test
    public void createPlanet_WithValidData_ReturnsPlanet(){
        // AAA

        // Arrange
        when(planetRepository.save(PLANET)).thenReturn(PLANET);

        // Act
        // system under test
        Planet sut = planetService.create(PLANET);

        // Assert
        assertThat(sut).isEqualTo(PLANET);
    }

}
