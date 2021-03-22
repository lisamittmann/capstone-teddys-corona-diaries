package de.neuefische.teddyscoronadiaries.covid19api.service;

import de.neuefische.teddyscoronadiaries.covid19api.model.ConfirmedCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class Covid19ApiService {

    private final RestTemplate restTemplate;
    private final String baseUrl = "https://api.covid19api.com/";

    @Autowired
    public Covid19ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ConfirmedCase> getConfirmedCases(String from, String to){

        String url = baseUrl + "country/germany/status/confirmed/live?from=" + from + "&to=" + to;

        try {
            ResponseEntity<ConfirmedCase[]> response = restTemplate.getForEntity(url, ConfirmedCase[].class);
            return List.of(response.getBody());
        } catch(Exception e) {
            return List.of();
        }
    }
}
