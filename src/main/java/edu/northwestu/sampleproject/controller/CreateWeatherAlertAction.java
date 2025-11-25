package edu.northwestu.sampleproject.controller;

import edu.northwestu.sampleproject.entity.WeatherAlert;
import edu.northwestu.sampleproject.repository.WeatherAlertRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CreateWeatherAlertAction {

    private final WeatherAlertRepository weatherAlertRepository;

    public CreateWeatherAlertAction(WeatherAlertRepository weatherAlertRepository) {
        this.weatherAlertRepository = weatherAlertRepository;
    }

    @PostMapping("/weather-alerts")
    public String createWeatherAlert(
            Model model,
            @Valid @ModelAttribute("dto") WeatherAlert weatherAlert,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            // Ensure the dto is properly set in the model for re-rendering the form
            model.addAttribute("dto", weatherAlert);
            return "weather/form";
        }

        this.weatherAlertRepository.save(weatherAlert);
        return "redirect:/";
    }
}
