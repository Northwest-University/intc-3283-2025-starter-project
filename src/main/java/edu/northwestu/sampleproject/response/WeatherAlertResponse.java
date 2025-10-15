package edu.northwestu.sampleproject.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class WeatherAlertResponse {

    @JsonProperty("features")
    List<WeatherAlertDto> alerts;

    public List<WeatherAlertDto> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<WeatherAlertDto> alerts) {
        this.alerts = alerts;
    }
}
