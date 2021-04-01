package de.neuefische.teddyscoronadiaries.openweatherapi.service;

import de.neuefische.teddyscoronadiaries.openweatherapi.config.OpenWeatherKeyConfig;
import de.neuefische.teddyscoronadiaries.openweatherapi.model.Weather;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class OpenWeatherApiService {

    private final RestTemplate restTemplate;
    private final OpenWeatherKeyConfig openWeatherKeyConfig;
    private String baseUrl = "http://api.openweathermap.org/data/2.5/weather";

    public OpenWeatherApiService(RestTemplate restTemplate, OpenWeatherKeyConfig openWeatherKeyConfig) {
        this.restTemplate = restTemplate;
        this.openWeatherKeyConfig = openWeatherKeyConfig;
    }

    public Optional<Weather> getWeatherForProvinceCapital(String capital) {
        try {
            String url = baseUrl + "?q=" + capital + "&appid=" + openWeatherKeyConfig.getApiKey() + "&units=metric";
            ResponseEntity<Weather> response = restTemplate.getForEntity(url, Weather.class);
            return Optional.of(response.getBody());
        } catch(Exception e) {
            return Optional.empty();
        }
    }

}
