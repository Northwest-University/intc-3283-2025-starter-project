package edu.northwestu.sampleproject.controller;

import edu.northwestu.sampleproject.service.WeatherService;
import edu.northwestu.sampleproject.util.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.context.Context;

@Controller
public class IndexController {

    private final WeatherService weatherService;

    private final EmailService emailService;


    public IndexController(
            WeatherService weatherService,
            EmailService emailService) {
        this.weatherService = weatherService;
        this.emailService = emailService;
    }

    @GetMapping("/")
    public String index() throws MessagingException {

        var context = new Context();
        context.setVariable("severity","major");
        context.setVariable("urgency", null);


        this.emailService.sendTemplateEmail(
                "me@someone.com",
                "This is an alert",
                "weather-alert",
                context
        );

        return "index-unauthenticated";
    }
}
