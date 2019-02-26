package com.codecool.swapi.controller;

import com.codecool.swapi.config.TemplateEngineUtil;
import com.codecool.swapi.models.Planet;
import com.codecool.swapi.models.PlanetPage;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(urlPatterns = {"/"})
public class PlanetController extends HttpServlet {

    private PlanetPage getPlanetData() {
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
//            System.out.println(contentAsString);
            ObjectMapper mapper = new ObjectMapper();
            PlanetPage planetPage = mapper.readValue(contentAsString, PlanetPage.class);
//            System.out.println(planetPage.toString());
            httpClient.stop();
            return planetPage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Planet> planets = getPlanetData().getResults();

        for (Planet planet : planets) {
            for(String url : planet.getResidents()){
                System.out.println(url);
            }
        }

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        context.setVariable("planets", planets);
        engine.process("index.html", context, response.getWriter());
    }
}

