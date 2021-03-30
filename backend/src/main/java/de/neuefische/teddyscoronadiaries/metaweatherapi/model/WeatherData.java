package de.neuefische.teddyscoronadiaries.metaweatherapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherData {

    private String id;
    @JsonProperty("weather_state_name")
    private String weatherStateName;
    @JsonProperty("weather_state_abbr")
    private String weatherStateAbbreviation;
    @JsonProperty("applicable_date")
    private String applicableDate;
    @JsonProperty("min_temp")
    private Double minTemperature;
    @JsonProperty("max_temp")
    private Double maxTemperature;
    @JsonProperty("the_temp")
    private Double currentTemperature;
    @JsonProperty("wind_speed")
    private Double windSpeed;
    @JsonProperty("humidity")
    private int humidity;
}
