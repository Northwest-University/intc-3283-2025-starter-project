package edu.northwestu.sampleproject.controller;

import edu.northwestu.sampleproject.entity.WeatherAlert;
import edu.northwestu.sampleproject.repository.WeatherAlertRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = CreateWeatherAlertAction.class, excludeAutoConfiguration = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
class CreateWeatherAlertActionTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WeatherAlertRepository weatherAlertRepository;

    @Test
    void shouldCreateWeatherAlert() throws Exception {
        mockMvc.perform(post("/weather-alerts")
                .param("externalId", "ab")
                .param("sender", "test sender")
                .param("description", "test description")
                .param("urgency", "not urgent")
                .param("effective", "2024-01-01T10:00")
                .param("expires", "2024-01-02T10:00"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(weatherAlertRepository).save(any(WeatherAlert.class));
    }

    @Test
    void shouldShowValidationErrorsForBlankRequiredFields() throws Exception {
        mockMvc.perform(post("/weather-alerts")
                .param("externalId", "")
                .param("sender", "")
                .param("description", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("weather/form"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("dto", "externalId"))
                .andExpect(model().attributeHasFieldErrors("dto", "sender"))
                .andExpect(model().attributeHasFieldErrors("dto", "description"));
    }

    @Test
    void shouldShowValidationErrorForInvalidUrgency() throws Exception {
        mockMvc.perform(post("/weather-alerts")
                .param("externalId", "ab")
                .param("sender", "test sender")
                .param("description", "test description")
                .param("urgency", "invalid-urgency"))
                .andExpect(status().isOk())
                .andExpect(view().name("weather/form"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("dto", "urgency"));
    }
}
