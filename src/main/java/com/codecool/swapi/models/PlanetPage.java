package com.codecool.swapi.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "count",
        "next",
        "previous",
        "results"
})
public class PlanetPage implements Serializable
{

    @JsonProperty("count")
    private Integer count;
    @JsonProperty("next")
    private String next;
    @JsonProperty("previous")
    private Object previous;
    @JsonProperty("results")
    private List<Planet> results = new ArrayList<Planet>();
    private final static long serialVersionUID = 7661845106916889280L;

    @JsonProperty("count")
    public Integer getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(Integer count) {
        this.count = count;
    }

    @JsonProperty("next")
    public String getNext() {
        return next;
    }

    @JsonProperty("next")
    public void setNext(String next) {
        this.next = next;
    }

    @JsonProperty("previous")
    public Object getPrevious() {
        return previous;
    }

    @JsonProperty("previous")
    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    @JsonProperty("results")
    public List<Planet> getResults() {
        return results;
    }

    @JsonProperty("results")
    public void setResults(List<Planet> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PlanetPage{");
        results.forEach(x -> sb.append(x.getName()).append(", "));
        sb.append('}');
        return sb.toString();
    }
}
