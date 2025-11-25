package edu.northwestu.sampleproject.controller;

import edu.northwestu.sampleproject.repository.WeatherAlertRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DeleteWeatherAlertAction {

    private final WeatherAlertRepository weatherAlertRepository;

    public DeleteWeatherAlertAction(WeatherAlertRepository weatherAlertRepository) {

        this.weatherAlertRepository = weatherAlertRepository;
    }

    @PostMapping("/weather-alerts/{id}/delete")
    public String deleteWeatherAlert(@PathVariable Long id) {

        // if no ID or not found ... just send them back optimistically. This should avoid a DB exception trying to delete a non-existent ID.
        if (null == id || this.weatherAlertRepository.findById(id).isEmpty()) {
            return "redirect:/";
        }

        weatherAlertRepository.deleteById(id);
        return "redirect:/";
    }
}
