package edu.northwestu.sampleproject.controller;

import edu.northwestu.sampleproject.entity.WeatherAlert;
import edu.northwestu.sampleproject.repository.WeatherAlertRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = IndexController.class, excludeAutoConfiguration = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WeatherAlertRepository weatherAlertRepository;

    @Test
    void shouldListAllWeatherAlerts() throws Exception {
        WeatherAlert alert1 = createTestAlert(1L, "alert-1");
        WeatherAlert alert2 = createTestAlert(2L, "alert-2");
        Page<WeatherAlert> alertPage = new PageImpl<>(Arrays.asList(alert1, alert2));

        when(weatherAlertRepository.findAll(any(Pageable.class))).thenReturn(alertPage);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index-unauthenticated"))
                .andExpect(model().attributeExists("alertPage"));
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
