package de.neuefische.teddyscoronadiaries.metaweatherapi.service;

import de.neuefische.teddyscoronadiaries.metaweatherapi.model.WeatherData;
import de.neuefische.teddyscoronadiaries.metaweatherapi.model.WeatherDataWrapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MetaWeatherApiServiceTest {

    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private static final String baseUrl = "https://www.metaweather.com";
    private final MetaWeatherApiService metaWeatherApiService = new MetaWeatherApiService(restTemplate);

    @Test
    @DisplayName("Get weather data returns list of consilidated weather data")
    public void getWeatherDataReturnsListOfWeatherData() {
        // Given
        String woeid = "656958";

        when(restTemplate.getForEntity(baseUrl + "/api/location/" + woeid, WeatherDataWrapper.class))
                .thenReturn(ResponseEntity.ok(new WeatherDataWrapper(getWeatherDataList())));

        // When
        List<WeatherData> weatherData = metaWeatherApiService.getWeatherData(woeid);

        // Then
        assertThat(weatherData, is(getWeatherDataList()));

    }

    @Test
    @DisplayName("Get weather data returns empty list when API unavailable")
    public void getWeatherDataReturnsEmptyList() {
        // Given
        String woeid = "656958";

        when(restTemplate.getForEntity(baseUrl + "/api/location/" + woeid, WeatherDataWrapper.class))
                .thenThrow(new RestClientException("Service not available"));

        // When
        List<WeatherData> weatherData = metaWeatherApiService.getWeatherData(woeid);

        // Then
        assertThat(weatherData, is(List.of()));

    }


    private List<WeatherData> getWeatherDataList() {
        return List.of(
                WeatherData.builder()
                        .id("123")
                        .weatherStateName("Clear")
                        .weatherStateAbbreviation("c")
                        .applicableDate("2021-03-21")
                        .minTemperature(5.9865)
                        .maxTemperature(20.345)
                        .currentTemperature(20.240000000000002)
                        .windSpeed(8.930032549754765)
                        .humidity(50)
                        .build(),
                WeatherData.builder()
                        .id("345")
                        .weatherStateName("Light Cloud")
                        .weatherStateAbbreviation("c")
                        .applicableDate("2021-03-22")
                        .minTemperature(6.485)
                        .maxTemperature(16.725)
                        .currentTemperature(15.93)
                        .windSpeed(2.884157589803547)
                        .humidity(70)
                        .build(),
                WeatherData.builder()
                        .id("567")
                        .weatherStateName("Heavy Cloud")
                        .weatherStateAbbreviation("hc")
                        .applicableDate("2021-03-23")
                        .minTemperature(3.875)
                        .maxTemperature(13.86)
                        .currentTemperature(12.065000000000001)
                        .windSpeed(4.708858778127734)
                        .humidity(71)
                        .build()
        );
    }

}