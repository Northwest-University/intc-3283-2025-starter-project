package edu.northwestu.sampleproject.controller;

import edu.northwestu.sampleproject.entity.WeatherAlert;
import edu.northwestu.sampleproject.repository.WeatherAlertRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

@Controller
public class UpdateWeatherAlertAction {

    private final WeatherAlertRepository weatherAlertRepository;

    public UpdateWeatherAlertAction(WeatherAlertRepository weatherAlertRepository) {
        this.weatherAlertRepository = weatherAlertRepository;
    }

    @PostMapping("/weather-alerts/id/{id}")
    public String updateWeatherAlert(
            @PathVariable("id") Long id,
            Model model,
            @Valid @ModelAttribute("dto") WeatherAlert changes,
            BindingResult bindingResult
    ) {
        // Why final? so nobody can change this variable later unintentionally.
        final Optional<WeatherAlert> weatherAlertOptional = this.weatherAlertRepository.findById(id);

        // we need to figure out if the DB returned something.
        if (weatherAlertOptional.isEmpty()) {
            // if not; throw a proper HTTP Status "exception".
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Weather alert not found");
        }

        // Does validation pass? If not; bring them back to the form.
        if (bindingResult.hasErrors()) {
            // if there is an error, we're sending back the form which depends on the changes sent up.
            // Set that back in the model (pushed into the template)
            // so the user can fix entries w/o losing their work.
            model.addAttribute("dto", changes);
            return "weather/form";
        }

        // great; no errors, now we can update the object in the DB.
        final WeatherAlert weatherAlertFromDatabase = weatherAlertOptional.get();

        // update each value that we want to be updated.

        // in this case; the sent isn't in the form for whatever reason ... so let's conditionally update it so we don't null it out.
        if (null != changes.getSent()) {
            weatherAlertFromDatabase.setSent(changes.getSent());
        }

        weatherAlertFromDatabase.setEffective(changes.getEffective());
        weatherAlertFromDatabase.setExpires(changes.getExpires());
        weatherAlertFromDatabase.setDescription(changes.getDescription());
        weatherAlertFromDatabase.setUrgency(changes.getUrgency());
        weatherAlertFromDatabase.setCertainty(changes.getCertainty());
        weatherAlertFromDatabase.setSender(changes.getSender());
        weatherAlertFromDatabase.setMessageType(changes.getMessageType());
        weatherAlertFromDatabase.setSeverity(changes.getSeverity());
        weatherAlertFromDatabase.setInstruction(changes.getInstruction());
        weatherAlertFromDatabase.setStatus(changes.getStatus());

        // persist to DB.
        this.weatherAlertRepository.save(weatherAlertFromDatabase);

        return "redirect:/weather-alerts/" + weatherAlertFromDatabase.getId();
    }
}
