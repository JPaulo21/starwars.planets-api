package com.jp.tests.api.starwars.planets.domain;

import static com.jp.tests.api.starwars.planets.common.PlanetConstants.PLANET;
import static com.jp.tests.api.starwars.planets.common.PlanetConstants.TATOOINE;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

@DataJpaTest // Cria e usa um banco em memória H2 para os testes,
// Necessário ter o H2 como dependência
// Injeta os components no contexto spring
public class PlanetRepositoryTest {

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @AfterEach // Método é executa a cada TESTE
    public void afterEach(){
        PLANET.setId(null);
    }

    @Test
    public void createPlanet_WithValidDate_ReturnsPlanet(){
        Planet planet = planetRepository.save(PLANET);

        Planet sut = testEntityManager.find(Planet.class, planet.getId());

        System.out.println(planet);

        assertThat(sut).isNotNull();
        assertThat(sut.getName()).isEqualTo(PLANET.getName());
        assertThat(sut.getClimate()).isEqualTo(PLANET.getClimate());
        assertThat(sut.getTerrain()).isEqualTo(PLANET.getTerrain());
    }

    @Test
    public void createPlanet_WithInvalidData_ThrowsException(){
        Planet emptyPlanet = new Planet();
        Planet invalidPlanet = new Planet("","","");

        assertThatThrownBy(() -> planetRepository.save(emptyPlanet)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> planetRepository.save(invalidPlanet)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void createPlanet_WithExistingName_ThorwsException(){
        Planet planet = testEntityManager.persistFlushFind(PLANET); //Salve no banco e consulta o mesmo
        /* Disclaimer: O testEntityManager fica monitorando o objeto planet, então o setId para null terá efeito,
                        pois o método sabe também faz atualização do objeto caso o id esteja preenchido */
        testEntityManager.detach(planet); // Desassocia o objeto da sessão do testEntityManager
        planet.setId(null);

        assertThatThrownBy(() ->  planetRepository.save(planet)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet() throws Exception {
        Planet planet = testEntityManager.persistFlushFind(PLANET);

        Optional<Planet> planetOpt = planetRepository.findById(planet.getId());

        assertThat(planetOpt).isNotEmpty();
        assertThat(planetOpt.get()).isEqualTo(planet);
    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnsEmpty() throws Exception {
        Optional<Planet> planetOptional = planetRepository.findById(1L);

        assertThat(planetOptional).isEmpty();
    }

    @Test
    public void getPlanet_ByExistingName_ReturnsPlanet(){
        Planet planet = testEntityManager.persistFlushFind(PLANET);

        Optional<Planet> planetOpt = planetRepository.findByName(planet.getName());

        assertThat(planetOpt).isNotEmpty();
        assertThat(planetOpt.get()).isEqualTo(planet);
    }

    @Test
    public void getPlanet_ByExistingName_ReturnsNotFound(){
        Optional<Planet> planetOpt = planetRepository.findByName("name");

        assertThat(planetOpt).isEmpty();
    }

    @Sql(scripts = "/import_planets.sql")
    @Test
    public void listPlanets_ReturnsFilteredPlanets(){
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues();
        Example<Planet> planetQueryWithoutFilters = Example.of(new Planet(), exampleMatcher); // Sem filtro
        Example<Planet> planetQueryWithFilters = Example.of(new Planet(TATOOINE.getClimate(), TATOOINE.getTerrain()), exampleMatcher); // Com filtro

        List<Planet> responseWithoutFilters = planetRepository.findAll(planetQueryWithoutFilters);
        List<Planet> responseWithFilters = planetRepository.findAll(planetQueryWithFilters);

        assertThat(responseWithoutFilters).isNotEmpty();
        assertThat(responseWithoutFilters).hasSize(3);

        assertThat(responseWithFilters).isNotEmpty();
        assertThat(responseWithFilters).hasSize(1);
        assertThat(responseWithFilters.get(0)).isEqualTo(TATOOINE);
    }

    @Test
    public void listPlanets_ReturnsNoPlanets(){
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues();
        Example<Planet> planetQuery = Example.of(new Planet(), exampleMatcher);

        List<Planet> response = planetRepository.findAll(planetQuery);

        assertThat(response).isEmpty();
    }

}
