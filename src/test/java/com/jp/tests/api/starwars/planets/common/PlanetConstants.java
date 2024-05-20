package com.jp.tests.api.starwars.planets.common;

import com.jp.tests.api.starwars.planets.domain.Planet;

public class PlanetConstants {

    public static final Planet PLANET = new Planet("name","climate","terrain");
    public static final Planet INVALID_PLANET = new Planet("","","");

}
