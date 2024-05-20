package com.jp.tests.api.starwars.planets.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static com.jp.tests.api.starwars.planets.common.PlanetConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest // Montar o contexto de aplicação do spring, ou seja todas as classes, para injetar as dependencias nas classes de testes
//@SpringBootTest(classes = PlanetService.class) // Monta o contexto especifico para o PlanetService
public class PlanetServiceTest {

    //@Autowired -> Contexto do spring
    @InjectMocks
    private PlanetService planetService;

    //@MockBean
    @Mock
    private PlanetRepository planetRepository;

    // Padrão de nomeclatura -> operacao_estado_retornoEsperado
    @Test
    @DisplayName("create Planet With Valid Data Returns Planet")
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

    @Test
    @DisplayName("create Planet With Invalid Data Throws Exception")
    public void createPlanet_WithInvalidData_ThrowsException(){
        when(planetRepository.save(INVALID_PLANET)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> planetService.create(INVALID_PLANET)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getPlanet_byExistingId_ReturnsPlanet(){
        // AAA - ARRANGE, ACT E ASSERT
        when(planetRepository.findById(anyLong())).thenReturn(Optional.of(PLANET));

        Optional<Planet> sut = planetService.get(1L);

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PLANET);
    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnEmpty(){
        when(planetRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Planet> sut = planetService.get(1L);

        assertThat(sut).isEmpty();

    }

    @Test
    public void getPlanet_ByExistingName_ReturnsPlanet() {
        // TODO implement
    }

    @Test
    public void getPlanet_ByUnexistingName_ReturnsEmpty() {
        // TODO implement
    }

}
