package com.jp.tests.api.starwars.planets.domain;

import static com.jp.tests.api.starwars.planets.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest // Cria e usa um banco em memória H2 para os testes,
// Necessário ter o H2 como dependência
// Injeta os components no contexto spring
public class PlanetRepositoryTest {

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private TestEntityManager testEntityManager;

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

}
