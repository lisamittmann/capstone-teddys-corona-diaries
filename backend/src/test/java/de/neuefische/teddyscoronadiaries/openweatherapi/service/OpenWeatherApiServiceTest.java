package de.neuefische.teddyscoronadiaries.openweatherapi.service;

import de.neuefische.teddyscoronadiaries.openweatherapi.config.OpenWeatherKeyConfig;
import de.neuefische.teddyscoronadiaries.openweatherapi.model.Weather;
import de.neuefische.teddyscoronadiaries.openweatherapi.model.WeatherContent;
import de.neuefische.teddyscoronadiaries.openweatherapi.model.WeatherTemperature;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OpenWeatherApiServiceTest {

    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final OpenWeatherKeyConfig openWeatherKeyConfig = mock(OpenWeatherKeyConfig.class);
    private final OpenWeatherApiService openWeatherApiService = new OpenWeatherApiService(restTemplate, openWeatherKeyConfig);
    private String baseUrl = "http://api.openweathermap.org/data/2.5/weather";

    @Test
    @DisplayName("Get weather for province capital should return weather")
    public void getWeatherForProvinceCapitalShouldReturnWeather() {
        // Given
        String capital = "Hamburg";

        when(openWeatherKeyConfig.getApiKey()).thenReturn("awesomeApiKey");
        when(restTemplate.getForEntity(baseUrl + "?q=" + capital + "&appid=awesomeApiKey&units=metric", Weather.class)).thenReturn(ResponseEntity.ok(
                new Weather(new WeatherContent("Rain", "Light rain", "01d"),
                        new WeatherTemperature(16.56, 18.87, 10.93, 20.34))
        ));

        // When
        Optional<Weather> result = openWeatherApiService.getWeatherForProvinceCapital(capital);

        // Then
        assertThat(result.get(), is(new Weather(
                new WeatherContent("Rain", "Light rain", "01d"),
                new WeatherTemperature(16.56, 18.87, 10.93, 20.34))
        ));

    }

}