package de.neuefische.teddyscoronadiaries.controller;

import de.neuefische.teddyscoronadiaries.db.ProvinceMongoDb;
import de.neuefische.teddyscoronadiaries.metaweatherapi.model.WeatherData;
import de.neuefische.teddyscoronadiaries.metaweatherapi.model.WeatherDataWrapper;
import de.neuefische.teddyscoronadiaries.model.province.ProvinceData;
import de.neuefische.teddyscoronadiaries.model.weather.ProvinceCapitalWeatherData;
import de.neuefische.teddyscoronadiaries.utils.CurrentDateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WeatherControllerTest {

    @LocalServerPort
    int serverPort;

    private String getUrl() {
        return "http://localhost:" + serverPort + "/api/weather";
    }

    @MockBean
    private CurrentDateUtils currentDateUtils;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private ProvinceMongoDb provinceMongoDb;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    public void setup() {
        provinceMongoDb.deleteAll();
    }

    @Test
    @DisplayName("Get province capital weather data should return weather data")
    public void getProvinceCapitalWeatherDataShouldReturnWeatherData() {
        // Given
        String province = "Hamburg";
        provinceMongoDb.save(new ProvinceData("Hamburg", "Hamburg", "656958"));
        when(currentDateUtils.getCurrentDay()).thenReturn("2021-03-21");
        when(restTemplate.getForEntity("https://www.metaweather.com/api/location/656958", WeatherDataWrapper.class))
                .thenReturn(ResponseEntity.ok(new WeatherDataWrapper(getWeatherDataList())));

        // When
        ResponseEntity<ProvinceCapitalWeatherData> response = testRestTemplate.getForEntity(getUrl() + "/" + province, ProvinceCapitalWeatherData.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new ProvinceCapitalWeatherData(
                "Hamburg",
                "Sonnig",
                6,
                20,
                20,
                "https://www.metaweather.com/static/img/weather/c.svg"
        )));
    }

    @Test
    @DisplayName("Get province capital weather data should throw error for unknown province")
    public void getProvinceCapitalWeatherDataShouldThrowErrorForUnknownProvince(){
        // Given
        String province = "Bielefeld";

        // When
        ResponseEntity<Void> response = testRestTemplate.getForEntity(getUrl() + "/" + province, Void.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));

    }

    @Test
    @DisplayName("Get province capital weather data should throw error when API unavailable")
    public void getProvinceCapitalWeatherdataShouldThrowErrorWhenApiUnavailable() {
        // Given
        String province = "Hamburg";
        provinceMongoDb.save(new ProvinceData("Hamburg", "Hamburg", "656958"));
        when(currentDateUtils.getCurrentDay()).thenReturn("2021-03-21");
        when(restTemplate.getForEntity("https://www.metaweather.com/api/location/656958", WeatherDataWrapper.class))
                .thenThrow(new RestClientException("API not available"));

        // When
        ResponseEntity<Void> response = testRestTemplate.getForEntity(getUrl() + "/" + province, Void.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
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