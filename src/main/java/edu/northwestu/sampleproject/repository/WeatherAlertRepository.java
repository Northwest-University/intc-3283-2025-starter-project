package edu.northwestu.sampleproject.repository;

import edu.northwestu.sampleproject.entity.WeatherAlert;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherAlertRepository extends CrudRepository<WeatherAlert, Long> {
}
