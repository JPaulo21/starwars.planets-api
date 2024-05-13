package com.jp.tests.api.starwars.planets.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "planets")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Planet {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String climate;
    private String terrain;

    public Planet(String name, String climate, String terrain){
        this.name = name;
        this.climate = climate;
        this.terrain = terrain;
    }

}
