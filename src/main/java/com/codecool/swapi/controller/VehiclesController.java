package com.codecool.swapi.controller;

import com.codecool.swapi.config.TemplateEngineUtil;
import com.codecool.swapi.models.Film;
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


@WebServlet(urlPatterns = {"/vehicles"})
public class VehiclesController extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        response.setCharacterEncoding("utf-8");
        String vehiclesUrl = request.getParameter("vehicles");
        if (vehiclesUrl != null) {
            List<String> vehiclesUrlList = Arrays.asList(vehiclesUrl.substring(1, vehiclesUrl.length() - 1).split(", "));
            List<Vehicle> vehicles = new ArrayList<>();
            for (String url : vehiclesUrlList) {
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
                    Vehicle vehicle = mapper.readValue(contentAsString, Vehicle.class);
                    vehicles.add(vehicle);
                    httpClient.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            context.setVariable("vehicles", vehicles);
            engine.process("vehicles.html", context, response.getWriter());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }
}
