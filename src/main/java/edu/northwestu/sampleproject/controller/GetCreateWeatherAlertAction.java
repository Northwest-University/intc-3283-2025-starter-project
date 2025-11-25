package edu.northwestu.sampleproject.controller;

import edu.northwestu.sampleproject.entity.WeatherAlert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GetCreateWeatherAlertAction {
    @GetMapping("/weather-alerts/new")
    public String createWeatherAlertForm(Model model) {
        var wa = new WeatherAlert();
        model.addAttribute("dto", wa);
        return "weather/form";
    }
}
