package com.jp.tests.api.starwars.planets.web;

import com.jp.tests.api.starwars.planets.domain.Planet;
import com.jp.tests.api.starwars.planets.domain.PlanetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<Planet> get(@PathVariable ("id") Long id){
        return planetService.get(id).map(planet -> ResponseEntity.ok(planet))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
