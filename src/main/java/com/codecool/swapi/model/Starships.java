package com.codecool.swapi.model;

import java.util.ArrayList;
import java.util.List;

public class Starships {

    private String name;
    private String model;
    private String manufacturer;
    private String cost_in_credits;
    private String length;
    private String max_atmosphering_speed;
    private String crew;
    private String passengers;
    private String cargo_capacity;
    private String consumables;
    private String hyperdrive_rating;
    private String MGLT;
    private String starship_class;
    List<People> pilots = new ArrayList<>();
    List<Film> films = new ArrayList<>();
    private String created;
    private String edited;
    private String url;

}
