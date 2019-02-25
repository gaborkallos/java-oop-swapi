package com.codecool.swapi.model;

import java.util.ArrayList;
import java.util.List;

public class People {

    private String name;
    private String height;
    private String mass;
    private String hair_color;
    private String skin_color;
    private String eye_color;
    private String birth_year;
    private String gender;
    private String homeworld;
    List<Film> films = new ArrayList<>();
    List<Vehicle> vehicles = new ArrayList<>();
    List<Starships> starships = new ArrayList<>();
    private String url;
}
