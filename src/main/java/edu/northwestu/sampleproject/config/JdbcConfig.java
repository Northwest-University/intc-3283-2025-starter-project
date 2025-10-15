package edu.northwestu.sampleproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import java.util.Arrays;

/**
 * Configuration class for Spring Data JDBC.
 * Enables JDBC repositories and auditing, and registers custom converters.
 */
@Configuration
@EnableJdbcRepositories(basePackages = "edu.northwestu.sampleproject.repository")
@EnableJdbcAuditing
public class JdbcConfig {

    /**
     * Registers custom converters for JDBC.
     * This allows Spring Data JDBC to convert between Java types and database types.
     *
     * @return the custom conversions
     */
    @Bean
    public JdbcCustomConversions jdbcCustomConversions() {
        return new JdbcCustomConversions(Arrays.asList(

        ));
    }
}
