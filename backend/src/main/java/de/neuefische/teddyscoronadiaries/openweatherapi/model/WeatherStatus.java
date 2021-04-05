package de.neuefische.teddyscoronadiaries.openweatherapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherStatus {
    @JsonProperty("main")
    private String category;
    @JsonProperty("description")
    private String description;
    @JsonProperty("icon")
    private String iconId;
}
