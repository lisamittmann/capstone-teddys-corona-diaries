package de.neuefische.teddyscoronadiaries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class TeddyscoronadiariesApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeddyscoronadiariesApplication.class, args);
    }

}
