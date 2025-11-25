package edu.northwestu.sampleproject.controller;

import edu.northwestu.sampleproject.entity.WeatherAlert;
import edu.northwestu.sampleproject.repository.WeatherAlertRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = UpdateWeatherAlertAction.class, excludeAutoConfiguration = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
class UpdateWeatherAlertActionTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WeatherAlertRepository weatherAlertRepository;

    @Test
    void shouldUpdateExistingWeatherAlert() throws Exception {
        WeatherAlert existingAlert = createTestAlert(1L, "ab");
        when(weatherAlertRepository.findById(1L)).thenReturn(Optional.of(existingAlert));

        mockMvc.perform(post("/weather-alerts/id/1")
                .param("externalId", "cd")
                .param("sender", "updated sender")
                .param("description", "updated description")
                .param("urgency", "super urgent")
                .param("effective", "2024-01-01T10:00")
                .param("expires", "2024-01-02T10:00"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/weather-alerts/1"));

        verify(weatherAlertRepository).save(any(WeatherAlert.class));
    }

    @Test
    void shouldShowValidationErrorsOnUpdate() throws Exception {
        WeatherAlert existingAlert = createTestAlert(1L, "ab");
        when(weatherAlertRepository.findById(1L)).thenReturn(Optional.of(existingAlert));

        mockMvc.perform(post("/weather-alerts/id/1")
                .param("externalId", "toolong")
                .param("sender", "")
                .param("description", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("weather/form"))
                .andExpect(model().hasErrors());
    }

    @Test
    void shouldReturn404WhenWeatherAlertNotFound() throws Exception {
        when(weatherAlertRepository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/weather-alerts/id/999")
                .param("externalId", "ab")
                .param("sender", "test sender")
                .param("description", "test description"))
                .andExpect(status().isNotFound());
    }

    private WeatherAlert createTestAlert(Long id, String externalId) {
        WeatherAlert alert = new WeatherAlert();
        alert.setId(id);
        alert.setExternalId(externalId);
        alert.setSender("test sender");
        alert.setDescription("test description");
        alert.setUrgency("not urgent");
        alert.setEffective(LocalDateTime.of(2024, 1, 1, 10, 0));
        alert.setExpires(LocalDateTime.of(2024, 1, 2, 10, 0));
        return alert;
    }
}
