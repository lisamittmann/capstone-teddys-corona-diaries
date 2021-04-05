package de.neuefische.teddyscoronadiaries.openweatherapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weather {

    private List<WeatherStatus> weather;
    @JsonProperty("main")
    private Temperature temperature;
}
