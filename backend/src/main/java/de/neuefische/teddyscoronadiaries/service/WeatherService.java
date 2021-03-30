package de.neuefische.teddyscoronadiaries.service;

import de.neuefische.teddyscoronadiaries.db.ProvinceMongoDb;
import de.neuefische.teddyscoronadiaries.metaweatherapi.model.WeatherData;
import de.neuefische.teddyscoronadiaries.metaweatherapi.service.MetaWeatherApiService;
import de.neuefische.teddyscoronadiaries.model.province.ProvinceData;
import de.neuefische.teddyscoronadiaries.model.weather.ProvinceCapitalWeatherData;
import de.neuefische.teddyscoronadiaries.model.weather.WeatherCategories;
import de.neuefische.teddyscoronadiaries.utils.CurrentDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class WeatherService {

    private final MetaWeatherApiService metaWeatherApiService;
    private final ProvinceMongoDb provinceMongoDb;
    private final CurrentDateUtils currentDateUtils;

    @Autowired
    public WeatherService(MetaWeatherApiService metaWeatherApiService, ProvinceMongoDb provinceMongoDb, CurrentDateUtils currentDateUtils) {
        this.metaWeatherApiService = metaWeatherApiService;
        this.provinceMongoDb = provinceMongoDb;
        this.currentDateUtils = currentDateUtils;
    }

    public ProvinceCapitalWeatherData getProvinceCapitalWeatherData(String province) {
        Optional<ProvinceData> provinceData = provinceMongoDb.findById(province);

        if (provinceData.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Province not known");
        }

        String capitalWoeid = provinceData.get().getCapitalWoeid();

        Optional<WeatherData> weatherDataForProvinceCapital = getWeatherDataForProvinceCapital(capitalWoeid);

        if(weatherDataForProvinceCapital.isEmpty()) {throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Weather API not available");}

        return getProvinceCapitalWeatherData(provinceData.get().getCapital(), weatherDataForProvinceCapital.get());
    }

    public Optional<WeatherData> getWeatherDataForProvinceCapital(String capitalWoeid) {
        List<WeatherData> weatherData = metaWeatherApiService.getWeatherData(capitalWoeid);

        String currentDay = currentDateUtils.getCurrentDay();

        return weatherData.stream().filter(dataSet -> dataSet.getApplicableDate().equals(currentDay)).findAny();
    }

    public ProvinceCapitalWeatherData getProvinceCapitalWeatherData(String capital, WeatherData data) {
        return new ProvinceCapitalWeatherData(
                capital,
                WeatherCategories.getWeatherCategory(data.getWeatherStateAbbreviation().toUpperCase()),
                (int)Math.round(data.getMinTemperature()),
                (int)Math.round(data.getMaxTemperature()),
                (int)Math.round(data.getCurrentTemperature())
        );
    }
}
