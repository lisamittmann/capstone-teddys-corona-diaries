package de.neuefische.teddyscoronadiaries.covid19api.service;

import de.neuefische.teddyscoronadiaries.covid19api.model.ConfirmedCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class Covid19ApiService {

    private final RestTemplate restTemplate;
    private final String baseUrl = "https://api.covid19api.com/";
    private final Instant quarantineStart = Instant.ofEpochSecond(1584230400L);

    @Autowired
    public Covid19ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ConfirmedCase> getConfirmedCases(String from, String to){

        //String to = quarantineStart.plus(quarantineDay, ChronoUnit.DAYS).toString();
        //String from = quarantineStart.plus(quarantineDay-6, ChronoUnit.DAYS).toString();
        String url = baseUrl + "country/germany/status/confirmed/live?from=" + from + "&to=" + to;

        try {
            ResponseEntity<ConfirmedCase[]> response = restTemplate.getForEntity(url, ConfirmedCase[].class);
            return List.of(response.getBody());
        } catch(Exception e) {
            return null;
        }
    }
}
