package com.codecool.swapi.controller;

import com.codecool.swapi.models.PlanetPage;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.util.ssl.SslContextFactory;

public class PlanetController {

    private void getPlanetData() {
        String url = "https://swapi.co/api/planets/";
        HttpClient httpClient = new HttpClient(new SslContextFactory());
        try {

            httpClient.start();
            Request req = httpClient.newRequest(url)
                    .method(HttpMethod.GET)
                    .agent("java client")
                    .header(HttpHeader.CONTENT_TYPE, "application/json");
            ContentResponse resp = req.send();

            String contentAsString = resp.getContentAsString();
            System.out.println(contentAsString);
            ObjectMapper mapper = new ObjectMapper();
            PlanetPage planetPage = mapper.readValue(contentAsString, PlanetPage.class);
            System.out.println(planetPage.toString());
            httpClient.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PlanetController pc = new PlanetController();
        pc.getPlanetData();
    }
}
