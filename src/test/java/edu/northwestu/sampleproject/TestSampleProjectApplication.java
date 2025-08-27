package edu.northwestu.sampleproject;

import org.springframework.boot.SpringApplication;

public class TestSampleProjectApplication {

    public static void main(String[] args) {
        SpringApplication.from(SampleProjectApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
