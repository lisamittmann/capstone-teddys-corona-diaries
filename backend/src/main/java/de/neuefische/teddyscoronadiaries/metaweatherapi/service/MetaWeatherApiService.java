package de.neuefische.teddyscoronadiaries.metaweatherapi.service;

import de.neuefische.teddyscoronadiaries.metaweatherapi.model.WeatherData;
import de.neuefische.teddyscoronadiaries.metaweatherapi.model.WeatherDataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MetaWeatherApiService {

    private final RestTemplate restTemplate;
    private static final String baseUrl = "https://www.metaweather.com";

    @Autowired
    public MetaWeatherApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public List<WeatherData> getWeatherData(String woeid) {
        try {
            ResponseEntity<WeatherDataWrapper> response = restTemplate.getForEntity(baseUrl + "/api/location/" + woeid, WeatherDataWrapper.class);
            return response.getBody().getConsolidatedWeather();
        } catch(Exception e) {
            return List.of();
        }
    }


}
