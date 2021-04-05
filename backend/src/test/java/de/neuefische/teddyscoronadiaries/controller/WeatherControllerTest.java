package de.neuefische.teddyscoronadiaries.controller;

import de.neuefische.teddyscoronadiaries.db.ProvinceMongoDb;
import de.neuefische.teddyscoronadiaries.model.province.ProvinceData;
import de.neuefische.teddyscoronadiaries.model.weather.ProvinceCapitalWeatherData;
import de.neuefische.teddyscoronadiaries.openweatherapi.model.Weather;
import de.neuefische.teddyscoronadiaries.openweatherapi.model.WeatherStatus;
import de.neuefische.teddyscoronadiaries.openweatherapi.model.Temperature;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"openweather.key=awesomeApiKey"})
class WeatherControllerTest {

    @LocalServerPort
    int serverPort;

    private String getUrl() {
        return "http://localhost:" + serverPort + "/api/weather";
    }

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
        String openWeatherUrl = "http://api.openweathermap.org/data/2.5/weather?q=Hamburg&appid=awesomeApiKey&units=metric&lang=DE";
        provinceMongoDb.save(new ProvinceData("Hamburg", "Hamburg"));
        when(restTemplate.getForEntity(openWeatherUrl, Weather.class))
                .thenReturn(ResponseEntity.ok(getWeather()));

        // When
        ResponseEntity<ProvinceCapitalWeatherData> response = testRestTemplate.getForEntity(getUrl() + "/" + province, ProvinceCapitalWeatherData.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new ProvinceCapitalWeatherData(
                "Hamburg",
                "Leichter Regen",
                11,
                20,
                17,
                "http://openweathermap.org/img/wn/01d@2x.png"
        )));
    }

    @Test
    @DisplayName("Get province capital weather data should throw error for unknown province")
    public void getProvinceCapitalWeatherDataShouldThrowErrorForUnknownProvince() {
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
        String openWeatherUrl = "http://api.openweathermap.org/data/2.5/weather?q=Hamburg&appid=awesomeApiKey&units=metric&lang=DE";
        provinceMongoDb.save(new ProvinceData("Hamburg", "Hamburg"));
        when(restTemplate.getForEntity(openWeatherUrl, Weather.class))
                .thenThrow(new RestClientException("API not available"));

        // When
        ResponseEntity<Void> response = testRestTemplate.getForEntity(getUrl() + "/" + province, Void.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }


    private Weather getWeather() {
        return new Weather(List.of(
                WeatherStatus.builder()
                        .category("Rain")
                        .description("Leichter Regen")
                        .iconId("01d")
                        .build()),
                Temperature.builder()
                        .currentTemperature(16.56)
                        .feelsLikeTemperature(18.87)
                        .minTemperature(10.93)
                        .maxTemperature(20.34)
                        .build());
    }

}