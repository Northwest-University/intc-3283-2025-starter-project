package edu.northwestu.sampleproject.repository;

import edu.northwestu.sampleproject.entity.WeatherAlert;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface WeatherAlertRepository extends CrudRepository<WeatherAlert, Long> {

    Optional<WeatherAlert> findOneByExternalIdEquals(String externalId);

}
