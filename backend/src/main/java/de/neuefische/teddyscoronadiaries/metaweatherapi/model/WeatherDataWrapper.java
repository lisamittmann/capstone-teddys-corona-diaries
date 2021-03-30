package de.neuefische.teddyscoronadiaries.metaweatherapi.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDataWrapper {

    @JsonProperty("consolidated_weather")
    private List<WeatherData> consolidatedWeather;
}
