package com.codecool.swapi.model;

import java.util.ArrayList;
import java.util.List;

public class Planet {

    private String name;
    private String rotation_period;
    private String orbital_period;
    private String diameter;
    private String climate;
    private String gravity;
    private String terrain;
    private String surface_water;
    private String population;
    List<People> residents = new ArrayList<>();
    List<Film> films = new ArrayList<>();
    private String url;
}
