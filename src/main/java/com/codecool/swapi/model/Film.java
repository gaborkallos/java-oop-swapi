package com.codecool.swapi.model;

import java.util.ArrayList;
import java.util.List;

public class Film {

    private String title;
    private float episode_id;
    private String opening_crawl;
    private String director;
    private String producer;
    private String release_date;
    List<People> characters = new ArrayList<>();
    List<Planet> planets = new ArrayList<>();
    List <Starships> starships = new ArrayList <> ();
    List<Vehicle>vehicles = new ArrayList < > ();
    private String url;
}
