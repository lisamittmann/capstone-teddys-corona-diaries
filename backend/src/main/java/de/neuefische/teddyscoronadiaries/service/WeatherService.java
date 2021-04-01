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
        Optional<Weather> weatherData = openWeatherApiService.getWeatherForProvinceCapital(capital);

        if(weatherData.isEmpty()) {throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Weather API not available");}

        return getProvinceCapitalWeatherDataFromWeatherData(provinceData.get().getCapital(), weatherData.get());
    }

    public ProvinceCapitalWeatherData getProvinceCapitalWeatherDataFromWeatherData(String capital, Weather data) {


        return new ProvinceCapitalWeatherData(
                capital,
                data.getWeather().get(0).getWeatherDescription(),
                (int)Math.round(data.getTemperature().getMinTemperature()),
                (int)Math.round(data.getTemperature().getMaxTemperature()),
                (int)Math.round(data.getTemperature().getCurrentTemperature()),
                "http://openweathermap.org/img/wn/" + data.getWeather().get(0).getIconId() + "@2x.png"
        );
    }
}
