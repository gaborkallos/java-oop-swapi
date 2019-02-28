package com.codecool.swapi.controller;

import com.codecool.swapi.config.TemplateEngineUtil;
import com.codecool.swapi.models.Starship;
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

@WebServlet(urlPatterns = {"/starships"})
public class StarshipsController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        response.setCharacterEncoding("utf-8");
        String starshipsURL = request.getParameter("starships");
        if (starshipsURL != null) {
            List<String> starsshipsURLList = Arrays.asList(starshipsURL.substring(1, starshipsURL.length() - 1).split(", "));
            List<Starship> starships = new ArrayList<>();
            for (String url : starsshipsURLList) {
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
                    Starship starship = mapper.readValue(contentAsString, Starship.class);
                    starships.add(starship);
                    httpClient.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            context.setVariable("starships", starships);
            engine.process("starships.html", context, response.getWriter());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
