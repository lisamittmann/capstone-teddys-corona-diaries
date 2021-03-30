package de.neuefische.teddyscoronadiaries.service;

import de.neuefische.teddyscoronadiaries.db.ProvinceMongoDb;
import de.neuefische.teddyscoronadiaries.metaweatherapi.model.WeatherData;
import de.neuefische.teddyscoronadiaries.metaweatherapi.service.MetaWeatherApiService;
import de.neuefische.teddyscoronadiaries.model.province.ProvinceData;
import de.neuefische.teddyscoronadiaries.model.weather.ProvinceCapitalWeatherData;
import de.neuefische.teddyscoronadiaries.utils.CurrentDateUtils;
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

    private final MetaWeatherApiService metaWeatherApiService = mock(MetaWeatherApiService.class);
    private final ProvinceMongoDb provinceMongoDb = mock(ProvinceMongoDb.class);
    private final CurrentDateUtils currentDateUtils = mock(CurrentDateUtils.class);
    private final WeatherService weatherService = new WeatherService(metaWeatherApiService, provinceMongoDb, currentDateUtils);

    @Test
    @DisplayName("get province capital weather data should return weather data")
    public void getProvinceCapitalWeatherDataReturnsWeatherData() {
        // Given
        String province = "Hamburg";
        String capitalWoeid = "656958";

        when(currentDateUtils.getCurrentDay()).thenReturn("2021-03-21");
        when(provinceMongoDb.findById(province)).thenReturn(java.util.Optional.of(new ProvinceData("Hamburg", "Hamburg", "656958")));
        when(metaWeatherApiService.getWeatherData(capitalWoeid)).thenReturn(getWeatherDataList());

        // When
        ProvinceCapitalWeatherData provinceCapitalWeatherData = weatherService.getProvinceCapitalWeatherData(province);

        // Then
        assertThat(provinceCapitalWeatherData, is(new ProvinceCapitalWeatherData(
                "Hamburg",
                "Sonnig",
                6,
                20,
                20,
                "https://www.metaweather.com/static/img/weather/c.svg"
        )));

    }

    @Test
    @DisplayName("Get province capital weather data should throw error when province is unknown")
    public void getProvinceCapitalWeatherdataShouldThrowErrorWhenProvinceIsUnknown() {
        // Given
        String province = "michGibtsNicht";
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
        String capitalWoeid = "656958";

        when(currentDateUtils.getCurrentDay()).thenReturn("2021-03-21");
        when(provinceMongoDb.findById(province)).thenReturn(java.util.Optional.of(new ProvinceData("Hamburg", "Hamburg", "656958")));
        when(metaWeatherApiService.getWeatherData(capitalWoeid)).thenReturn(List.of());

        // Then
        assertThrows(ResponseStatusException.class, () -> {
            weatherService.getProvinceCapitalWeatherData(province);
        });

    }

    @Test
    @DisplayName("Get weather data for province capital should return correct weather data set")
    public void getWeatherdataForProvinceCapitalShouldReturnWeatherDataSet() {
        // Given
        String capitalWoeid = "656958";
        when(currentDateUtils.getCurrentDay()).thenReturn("2021-03-21");
        when(metaWeatherApiService.getWeatherData(capitalWoeid)).thenReturn(getWeatherDataList());

        // When
        Optional<WeatherData> result = weatherService.getWeatherDataForProvinceCapital(capitalWoeid);

        // Then
        assertThat(result.get(), is(WeatherData.builder()
                .id("123")
                .weatherStateName("Clear")
                .weatherStateAbbreviation("c")
                .applicableDate("2021-03-21")
                .minTemperature(5.9865)
                .maxTemperature(20.345)
                .currentTemperature(20.240000000000002)
                .windSpeed(8.930032549754765)
                .humidity(50)
                .build()));

    }

    @Test
    @DisplayName("Get province capital weather data from weather data should return correct object")
    public void getProvinceCpitalWeatherDataFromWeatherDataTest() {
        // Given
        String capital = "Hamburg";
        WeatherData weatherData = WeatherData.builder()
                .id("123")
                .weatherStateName("Clear")
                .weatherStateAbbreviation("c")
                .applicableDate("2021-03-21")
                .minTemperature(5.9865)
                .maxTemperature(20.345)
                .currentTemperature(20.240000000000002)
                .windSpeed(8.930032549754765)
                .humidity(50)
                .build();

        // When
        ProvinceCapitalWeatherData result = weatherService.getProvinceCapitalWeatherDataFromWeatherData(capital, weatherData);

        // Then
        assertThat(result, is(new ProvinceCapitalWeatherData(
                "Hamburg",
                "Sonnig",
                6,
                20,
                20,
                "https://www.metaweather.com/static/img/weather/c.svg"
        )));

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