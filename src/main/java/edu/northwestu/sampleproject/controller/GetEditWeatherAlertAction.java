package edu.northwestu.sampleproject.controller;

import edu.northwestu.sampleproject.entity.WeatherAlert;
import edu.northwestu.sampleproject.repository.WeatherAlertRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GetEditWeatherAlertAction {

    private final WeatherAlertRepository weatherAlertRepository;

    public GetEditWeatherAlertAction(WeatherAlertRepository weatherAlertRepository) {

        this.weatherAlertRepository = weatherAlertRepository;
    }

    @GetMapping("/weather-alerts/{id}/edit")
    public String editWeatherAlertForm(@PathVariable Long id, Model model) {

        WeatherAlert weatherAlert = weatherAlertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Weather alert not found"));
        model.addAttribute("dto", weatherAlert);
        return "weather/form";
    }


}
