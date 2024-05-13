package com.jp.tests.api.starwars.planets.web;

import com.jp.tests.api.starwars.planets.domain.Planet;
import com.jp.tests.api.starwars.planets.domain.PlanetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/planets")
@RequiredArgsConstructor
public class PlanetController {

    private final PlanetService planetService;

    @PostMapping
    public ResponseEntity<Planet> create(@RequestBody Planet planetData){
        Planet planet = planetService.create(planetData);
        return ResponseEntity.status(HttpStatus.CREATED).body(planet);
    }
}
