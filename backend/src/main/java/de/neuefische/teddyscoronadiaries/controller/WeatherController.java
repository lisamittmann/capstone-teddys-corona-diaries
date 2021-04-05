package de.neuefische.teddyscoronadiaries.controller;

import de.neuefische.teddyscoronadiaries.model.weather.ProvinceCapitalWeatherData;
import de.neuefische.teddyscoronadiaries.openweatherapi.model.Weather;
import de.neuefische.teddyscoronadiaries.openweatherapi.service.OpenWeatherApiService;
import de.neuefische.teddyscoronadiaries.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/weather")
public class WeatherController {

    private final WeatherService weatherService;
    private final OpenWeatherApiService openWeatherApiService;

    @Autowired
    public WeatherController(WeatherService weatherService, OpenWeatherApiService openWeatherApiService) {
        this.weatherService = weatherService;
        this.openWeatherApiService = openWeatherApiService;
    }

    @GetMapping("{province}")
    public ProvinceCapitalWeatherData getProvinceCapitalWeatherData(@PathVariable String province) {
        return weatherService.getProvinceCapitalWeatherData(province);
    }
}
