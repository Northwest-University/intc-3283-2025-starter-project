package edu.northwestu.sampleproject.controller;

import edu.northwestu.sampleproject.entity.WeatherAlert;
import edu.northwestu.sampleproject.repository.WeatherAlertRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DeleteWeatherAlertAction.class, excludeAutoConfiguration = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
class DeleteWeatherAlertActionTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WeatherAlertRepository weatherAlertRepository;

    @Test
    void shouldDeleteWeatherAlert() throws Exception {
        WeatherAlert alert = new WeatherAlert();
        when(weatherAlertRepository.findById(1L)).thenReturn(Optional.of(alert));

        mockMvc.perform(post("/weather-alerts/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(weatherAlertRepository).findById(1L);
        verify(weatherAlertRepository).deleteById(1L);
    }
}
