package de.neuefische.teddyscoronadiaries.service;

import de.neuefische.teddyscoronadiaries.db.ProvinceMongoDb;
import de.neuefische.teddyscoronadiaries.metaweatherapi.service.MetaWeatherApiService;
import de.neuefische.teddyscoronadiaries.model.weather.ProvinceCapitalWeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    private final MetaWeatherApiService metaWeatherApiService;
    private final ProvinceMongoDb provinceMongoDb;

    @Autowired
    public WeatherService(MetaWeatherApiService metaWeatherApiService, ProvinceMongoDb provinceMongoDb) {
        this.metaWeatherApiService = metaWeatherApiService;
        this.provinceMongoDb = provinceMongoDb;
    }

    public ProvinceCapitalWeatherData getProvinceCapitalWeatherData(String province) {
        return null;
    }
}
