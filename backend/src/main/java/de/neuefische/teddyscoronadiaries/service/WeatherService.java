package de.neuefische.teddyscoronadiaries.service;

import de.neuefische.teddyscoronadiaries.db.ProvinceMongoDb;
import de.neuefische.teddyscoronadiaries.model.province.ProvinceData;
import de.neuefische.teddyscoronadiaries.model.weather.ProvinceCapitalWeatherData;
import de.neuefische.teddyscoronadiaries.openweatherapi.model.Weather;
import de.neuefische.teddyscoronadiaries.openweatherapi.service.OpenWeatherApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class WeatherService {

    private final OpenWeatherApiService openWeatherApiService;
    private final ProvinceMongoDb provinceMongoDb;


    @Autowired
    public WeatherService(OpenWeatherApiService openWeatherApiService, ProvinceMongoDb provinceMongoDb) {
        this.openWeatherApiService = openWeatherApiService;
        this.provinceMongoDb = provinceMongoDb;
    }

    public ProvinceCapitalWeatherData getProvinceCapitalWeatherData(String province) {
        Optional<ProvinceData> provinceData = provinceMongoDb.findById(province);

        if (provinceData.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Province not known");
        }

        String capital = provinceData.get().getCapital();
        Weather weatherData = openWeatherApiService.getWeatherForProvinceCapital(capital).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Weather API not available");
        });

        return getProvinceCapitalWeatherDataFromWeatherData(provinceData.get().getCapital(), weatherData);
    }

    public ProvinceCapitalWeatherData getProvinceCapitalWeatherDataFromWeatherData(String capital, Weather weatherData) {


        return new ProvinceCapitalWeatherData(
                capital,
                weatherData.getWeather().get(0).getCategory(),
                weatherData.getWeather().get(0).getDescription(),
                (int)Math.round(weatherData.getTemperature().getMinTemperature()),
                (int)Math.round(weatherData.getTemperature().getMaxTemperature()),
                (int)Math.round(weatherData.getTemperature().getCurrentTemperature()),
                getIconUrl(weatherData.getWeather().get(0).getIconId())
        );
    }

    private String getIconUrl(String iconId) {
        return "http://openweathermap.org/img/wn/" + iconId + "@2x.png";
    }
}
