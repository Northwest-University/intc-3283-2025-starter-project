package edu.northwestu.sampleproject.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherAlertDto {
    private String id;

    @JsonProperty("properties")
    private WeatherAlertProperties properties;

    public WeatherAlertProperties getProperties() {
        return properties;
    }

    public void setProperties(WeatherAlertProperties properties) {
        this.properties = properties;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
