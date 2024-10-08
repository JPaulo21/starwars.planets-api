package com.jp.tests.api.starwars.planets.domain;

import com.jp.tests.api.starwars.planets.domain.exceptions.PlanetNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanetService {

    private final PlanetRepository planetRepository;

    public Planet create(Planet planet){
        return planetRepository.save(planet);
    }

    public Optional<Planet> get(Long id) {
        return planetRepository.findById(id);
    }

    public Optional<Planet> getByName(String name){
        return planetRepository.findByName(name);
    }

    public List<Planet> list(String terrain, String climate) {
        Planet planet = Planet.builder().terrain(terrain).climate(climate).build();
        //ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues();
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase().withIgnoreNullValues();
        Example<Planet> planetQuery = Example.of(planet, exampleMatcher);
        return planetRepository.findAll(planetQuery);
    }

    public void remove(Long id) {
        if(!planetRepository.existsById(id))
            new PlanetNotFoundException("Planet id="+id+" not exists");

        planetRepository.deleteById(id);
    }
}
