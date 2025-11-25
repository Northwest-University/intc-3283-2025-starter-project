package edu.northwestu.sampleproject.repository;

import edu.northwestu.sampleproject.entity.WeatherAlert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import edu.northwestu.sampleproject.TestcontainersConfiguration;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@Import(TestcontainersConfiguration.class)
public class WeatherAlertRepositoryTest {

    @Autowired
    private WeatherAlertRepository repository;

    @Test
    void testCreateReadUpdateDelete() {
        // CREATE
        WeatherAlert alert = new WeatherAlert();
        alert.setExternalId("test-123");
        alert.setEffective(LocalDateTime.now().plusHours(1));
        alert.setExpires(LocalDateTime.now().plusHours(24));
        alert.setStatus("Actual");
        alert.setMessageType("Alert");
        alert.setSeverity("Moderate");
        alert.setCertainty("Likely");
        alert.setUrgency("not urgent");
        alert.setSender("test sender");
        alert.setDescription("test description");

        WeatherAlert saved = repository.save(alert);
        assertThat(saved.getId()).isNotNull();

        // READ
        Optional<WeatherAlert> found = repository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getExternalId()).isEqualTo("test-123");

        // UPDATE
        found.get().setDescription("updated description");
        WeatherAlert updated = repository.save(found.get());
        assertThat(updated.getDescription()).isEqualTo("updated description");

        // DELETE
        repository.deleteById(saved.getId());
        assertThat(repository.findById(saved.getId())).isEmpty();
    }
}
