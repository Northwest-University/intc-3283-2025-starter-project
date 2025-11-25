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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = GetEditWeatherAlertAction.class, excludeAutoConfiguration = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
class GetEditWeatherAlertActionTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WeatherAlertRepository weatherAlertRepository;

    @Test
    void shouldDisplayEditForm() throws Exception {
        WeatherAlert existingAlert = createTestAlert(1L, "existing-alert");
        when(weatherAlertRepository.findById(1L)).thenReturn(Optional.of(existingAlert));

        mockMvc.perform(get("/weather-alerts/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("weather/form"))
                .andExpect(model().attributeExists("dto"));
    }

    private WeatherAlert createTestAlert(Long id, String externalId) {
        WeatherAlert alert = new WeatherAlert();
        alert.setExternalId(externalId);
        alert.setSender("test-sender");
        alert.setDescription("test-description");
        alert.setUrgency("not urgent");
        alert.setEffective(LocalDateTime.of(2024, 1, 1, 10, 0));
        alert.setExpires(LocalDateTime.of(2024, 1, 2, 10, 0));
        return alert;
    }
}
