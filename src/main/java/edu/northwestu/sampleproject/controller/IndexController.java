package edu.northwestu.sampleproject.controller;

import edu.northwestu.sampleproject.response.WeatherAlertResponse;
import edu.northwestu.sampleproject.service.WeatherService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private final WeatherService weatherService;

    public IndexController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/")
    public String index() {


        this.weatherService.fetchAndSaveWeatherAlerts();


        //WeatherAlertResponse response = this.weatherService.fetchWeatherAlerts();


        return "index-unauthenticated";
    }
}
