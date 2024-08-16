package com.jp.tests.api.starwars.planets.domain.exceptions;

public class PlanetNotFoundException extends RuntimeException{

    public PlanetNotFoundException(String message){
        super(message);
    }

}
