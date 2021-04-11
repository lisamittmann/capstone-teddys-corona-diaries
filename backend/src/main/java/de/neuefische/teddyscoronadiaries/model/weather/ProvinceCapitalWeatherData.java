package de.neuefische.teddyscoronadiaries.model.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProvinceCapitalWeatherData {

    private String capital;
    private String weatherCategory;
    private String weatherState;
    private int minTemperature;
    private int maxTemperature;
    private int currentTemperature;
    private String weatherIconUrl;
}
