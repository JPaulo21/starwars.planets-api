package com.jp.tests.api.starwars.planets.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanetService {

    private final PlanetRepository planetRepository;

    public Planet create(Planet planet){
        return planetRepository.save(planet);
    }
}
