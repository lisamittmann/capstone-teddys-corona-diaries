package de.neuefische.teddyscoronadiaries.openweatherapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherTemperature {

    @JsonProperty("temp")
    private Double currentTemperature;
    @JsonProperty("feels_like")
    private Double feelsLikeTemperature;
    @JsonProperty("temp_min")
    private Double minTemperature;
    @JsonProperty("temp_max")
    private Double maxTemperature;
}
