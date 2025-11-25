package edu.northwestu.sampleproject.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = GetCreateWeatherAlertAction.class, excludeAutoConfiguration = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
class GetCreateWeatherAlertActionTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldDisplayCreateForm() throws Exception {
        mockMvc.perform(get("/weather-alerts/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("weather/form"))
                .andExpect(model().attributeExists("dto"));
    }
}
