package edu.northwestu.sampleproject.service;

import edu.northwestu.sampleproject.entity.WeatherAlert;
import edu.northwestu.sampleproject.repository.WeatherAlertRepository;
import edu.northwestu.sampleproject.response.WeatherAlertDto;
import edu.northwestu.sampleproject.response.WeatherAlertResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;
    private final WeatherAlertRepository repo;


    public WeatherService(RestTemplate restTemplate, WeatherAlertRepository repo) {

        this.restTemplate = restTemplate;
        this.repo = repo;
    }


    public void fetchAndSaveWeatherAlerts() {
        WeatherAlertResponse response = this.fetchWeatherAlerts();
        response.getAlerts().forEach(this::saveWeatherAlert);
    }


    public WeatherAlert saveWeatherAlert(WeatherAlertDto alert) {
        WeatherAlert weatherAlert = new WeatherAlert();
        weatherAlert.setExternalId(alert.getId());
        weatherAlert.setCertainty(alert.getProperties().getCertainty());
        weatherAlert.setUrgency(alert.getProperties().getUrgency());
        weatherAlert.setSender(alert.getProperties().getSender());
        weatherAlert.setDescription(alert.getProperties().getDescription());
        weatherAlert.setSent(alert.getProperties().getSent());
        weatherAlert.setEffective(alert.getProperties().getEffective());
        weatherAlert.setExpires(alert.getProperties().getExpires());
        weatherAlert.setMessageType(alert.getProperties().getMessageType());
        weatherAlert.setSeverity(alert.getProperties().getSeverity());
        weatherAlert.setInstruction(alert.getProperties().getInstruction());
        weatherAlert.setStatus(alert.getProperties().getStatus());
        return this.repo.save(weatherAlert);
    }


    public Iterable<WeatherAlert> fetchWeatherAlertsFromDB() {
        return this.repo.findAll();
    }

    public WeatherAlertResponse fetchWeatherAlerts() {
        return this.restTemplate.getForObject("https://api.weather.gov/alerts/active", WeatherAlertResponse.class);
    }

}
