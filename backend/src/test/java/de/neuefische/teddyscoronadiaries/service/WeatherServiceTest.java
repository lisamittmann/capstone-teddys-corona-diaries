package de.neuefische.teddyscoronadiaries.service;

import de.neuefische.teddyscoronadiaries.db.ProvinceMongoDb;
import de.neuefische.teddyscoronadiaries.model.province.ProvinceData;
import de.neuefische.teddyscoronadiaries.model.weather.ProvinceCapitalWeatherData;
import de.neuefische.teddyscoronadiaries.openweatherapi.model.Weather;
import de.neuefische.teddyscoronadiaries.openweatherapi.model.WeatherContent;
import de.neuefische.teddyscoronadiaries.openweatherapi.model.WeatherTemperature;
import de.neuefische.teddyscoronadiaries.openweatherapi.service.OpenWeatherApiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WeatherServiceTest {

    private final OpenWeatherApiService openWeatherApiService = mock(OpenWeatherApiService.class);
    private final ProvinceMongoDb provinceMongoDb = mock(ProvinceMongoDb.class);
    private final WeatherService weatherService = new WeatherService(openWeatherApiService, provinceMongoDb);

    @Test
    @DisplayName("get province capital weather data should return weather data")
    public void getProvinceCapitalWeatherDataReturnsWeatherData() {
        // Given
        String province = "Hamburg";
        String capital = "Hamburg";

        when(provinceMongoDb.findById(province)).thenReturn(java.util.Optional.of(new ProvinceData("Hamburg", "Hamburg", "656958")));
        when(openWeatherApiService.getWeatherForProvinceCapital(capital)).thenReturn(Optional.of(getWeather()));

        // When
        ProvinceCapitalWeatherData provinceCapitalWeatherData = weatherService.getProvinceCapitalWeatherData(province);

        // Then
        assertThat(provinceCapitalWeatherData, is(new ProvinceCapitalWeatherData(
                "Hamburg",
                "Leichter Regen",
                11,
                20,
                17,
                "http://openweathermap.org/img/wn/01d@2x.png"
        )));

    }

    @Test
    @DisplayName("Get province capital weather data should throw error when province is unknown")
    public void getProvinceCapitalWeatherdataShouldThrowErrorWhenProvinceIsUnknown() {
        // Given
        String province = "Bielefeld";
        when(provinceMongoDb.findById(province)).thenReturn(Optional.empty());

        // Then
        assertThrows(ResponseStatusException.class, () -> {
            weatherService.getProvinceCapitalWeatherData(province);
        });

    }

    @Test
    @DisplayName("Get province capital weather data should throw error when API is unavailable")
    public void getProvinceCapitalWeatherDataShouldThrowErrorWhenApiIsUnavailable() {
        // Given
        String province = "Hamburg";
        String capital = "Hamburg";

        when(provinceMongoDb.findById(province)).thenReturn(java.util.Optional.of(new ProvinceData("Hamburg", "Hamburg", "656958")));
        when(openWeatherApiService.getWeatherForProvinceCapital(capital)).thenReturn(Optional.empty());

        // Then
        assertThrows(ResponseStatusException.class, () -> {
            weatherService.getProvinceCapitalWeatherData(province);
        });

    }

    @Test
    @DisplayName("Get province capital weather data from weather data should return correct object")
    public void getProvinceCapitalWeatherDataFromWeatherDataTest() {
        // Given
        String capital = "Hamburg";
        Weather weather = getWeather();

        // When
        ProvinceCapitalWeatherData result = weatherService.getProvinceCapitalWeatherDataFromWeatherData(capital, weather);

        // Then
        assertThat(result, is(new ProvinceCapitalWeatherData(
                "Hamburg",
                "Leichter Regen",
                11,
                20,
                17,
                "http://openweathermap.org/img/wn/01d@2x.png"
        )));

    }


    private Weather getWeather() {
        return new Weather(List.of(new WeatherContent("Rain", "Leichter Regen", "01d")),
                new WeatherTemperature(16.56, 18.87, 10.93, 20.34));
    }

}