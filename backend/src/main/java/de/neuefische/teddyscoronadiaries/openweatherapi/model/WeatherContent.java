package de.neuefische.teddyscoronadiaries.openweatherapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherContent {
    @JsonProperty("main")
    private String weatherClass;
    @JsonProperty("description")
    private String weatherDescription;
    @JsonProperty("icon")
    private String iconId;
}
