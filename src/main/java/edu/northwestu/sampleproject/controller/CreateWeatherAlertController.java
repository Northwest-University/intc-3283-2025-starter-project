package edu.northwestu.sampleproject.controller;

import edu.northwestu.sampleproject.entity.WeatherAlert;
import edu.northwestu.sampleproject.repository.WeatherAlertRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.time.LocalDateTime;

@Controller
public class CreateWeatherAlertController {

    private final WeatherAlertRepository weatherAlertRepository;

    public CreateWeatherAlertController(final WeatherAlertRepository weatherAlertRepository) {
        this.weatherAlertRepository = weatherAlertRepository;
    }

    @GetMapping("/form")
    public String createWeatherAlertForm(Model model) {
        model.addAttribute("dto", new WeatherAlert());
        return "weather/form";
    }

    @PostMapping("/create-weather-alert")
    public String createWeatherAlert(
            Model model,
            WeatherAlert weatherAlert,
            BindingResult bindingResult
    ) {
        model.addAttribute("dto", weatherAlert);
        if (bindingResult.hasErrors()) {
            return "weather/form";
        }

        weatherAlert.setCertainty("certain");
        weatherAlert.setSeverity("severe");
        weatherAlert.setStatus("some status");
        weatherAlert.setDescription("some description");
        weatherAlert.setUrgency("super urgent");
        weatherAlert.setMessageType("notification");
        weatherAlert.setInstruction("do this");
        weatherAlert.setSent(LocalDateTime.now());
        weatherAlert.setEffective(LocalDateTime.now().plusDays(1));
        weatherAlert.setExpires(LocalDateTime.now().plusDays(4));
        weatherAlert.setSender("me");
        this.weatherAlertRepository.save(weatherAlert);

        return "redirect:/";

    }
}
