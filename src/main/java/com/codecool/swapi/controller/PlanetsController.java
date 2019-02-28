package com.codecool.swapi.controller;

import com.codecool.swapi.config.TemplateEngineUtil;
import com.codecool.swapi.models.Planet;
import com.codecool.swapi.models.PlanetPage;
import com.codecool.swapi.models.Vehicle;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@WebServlet(urlPatterns = {"/"})
public class PlanetsController extends HttpServlet {

    private int pageNumber = 1;

    private PlanetPage getPlanetData() {

        String url = "https://swapi.co/api/planets/?page=" + pageNumber;
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

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        context.setVariable("pagenumber", pageNumber);
        context.setVariable("planets", Objects.requireNonNull(getPlanetData()).getResults());
        context.setVariable("navbar", true);
        engine.process("index.html", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("next") != null || request.getParameter("previous") != null) {
            if (request.getParameter("next") != null) {
                pageNumber++;
            } else if (request.getParameter("previous") != null) {
                pageNumber--;
            } else {
                // TODO error handling
            }
            response.sendRedirect("/");
        } else if (request.getParameter("planets") != null) {
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
            WebContext context = new WebContext(request, response, request.getServletContext());
            response.setCharacterEncoding("utf-8");
            String planetsURL = request.getParameter("planets");
            if (planetsURL != null) {
                List<String> planetsUrlList = Arrays.asList(planetsURL.substring(1, planetsURL.length() - 1).split(", "));
                List<Planet> planets = new ArrayList<>();
                for (String url : planetsUrlList) {
                    HttpClient httpClient = new HttpClient(new SslContextFactory());

                    try {
                        httpClient.start();
                        Request req = httpClient.newRequest(url)
                                .method(HttpMethod.GET)
                                .agent("java client")
                                .header(HttpHeader.CONTENT_TYPE, "application/json");
                        ContentResponse resp = req.send();

                        String contentAsString = resp.getContentAsString();
                        ObjectMapper mapper = new ObjectMapper();
                        Planet planet= mapper.readValue(contentAsString, Planet.class);
                        planets.add(planet);
                        httpClient.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                context.setVariable("planets", planets);
                context.setVariable("navbar", false);
                engine.process("index.html", context, response.getWriter());
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            // TODO error handling
        }
    }
}



