package edu.northwestu.sampleproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/users/**",
                                "/users",
                                "/actuator/health",
                                "/favicon.ico",
                                "/error",
                                "/",
                                "/error/**",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/webjars/**",
                                "/**" // <-- This is important because we're basically authorizing everything for now.
                        )
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**")
                )
                .logout(logout -> logout
                        .logoutUrl("/authentication/logout")
                        .logoutSuccessUrl("/")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }
}
