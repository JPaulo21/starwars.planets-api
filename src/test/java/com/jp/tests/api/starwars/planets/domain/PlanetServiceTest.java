package com.jp.tests.api.starwars.planets.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.jp.tests.api.starwars.planets.common.PlanetConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = PlanetServiceTest.class)
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
        when(planetRepository.findByName(PLANET.getName())).thenReturn(Optional.of(PLANET));

        Optional<Planet> sut = planetService.getByName(PLANET.getName());

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PLANET);
    }

    @Test
    public void getPlanet_ByUnexistingName_ReturnsEmpty() {
        final String name = "Unexisting name";
        when(planetRepository.findByName(name)).thenReturn(Optional.empty());

        Optional<Planet> sut = planetService.getByName(name);

        assertThat(sut).isEmpty();
    }

    @Test
    public void listPlanets_ReturnAllPlanets(){
        List<Planet> planets = List.of(PLANET);
        when(planetRepository.findAll((Example<Planet>) any())).thenReturn(planets);

        List<Planet> sut = planetService.list(PLANET.getTerrain(),PLANET.getClimate());

        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);
        assertThat(sut.get(0)).isEqualTo(PLANET);
    }

    @Test
    public void listPlanets_ReturnNoPlanets(){
        when(planetRepository.findAll((Example<Planet>) any())).thenReturn(Collections.emptyList());

        List<Planet> sut = planetService.list(PLANET.getTerrain(),PLANET.getClimate());

        assertThat(sut).isEmpty();
    }

    @Test
    public void removePlanet_WithExistingId_doesNotThrowAnyException(){
        // o código não lança nenhuma exception
        assertThatCode(() -> planetRepository.deleteById(anyLong())).doesNotThrowAnyException();
    }

    @Test
    public void removePlanet_WithUnexistingId_ThrowsException(){
        // doThrow está sendo usando por o método deleteById retorna void, então estamos dizendo o tipo de retorno, nesse caso uma exception
        doThrow(new RuntimeException()).when(planetRepository).deleteById(anyLong());

        assertThatThrownBy(() -> planetService.remove(anyLong())).isInstanceOf(RuntimeException.class);
    }

}
