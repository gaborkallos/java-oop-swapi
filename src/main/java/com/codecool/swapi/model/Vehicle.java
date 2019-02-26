package com.codecool.swapi.model;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
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
    private String vehicle_class;
    List<People> pilots = new ArrayList<>();
    ArrayList<Film> films = new ArrayList<>();
    private String url;
}
