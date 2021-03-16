package de.neuefische.teddyscoronadiaries.covid19api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmedCase {
    @JsonProperty("Status")
    private String status;
    @JsonProperty("Cases")
    private int cases;
    @JsonProperty("Date")
    private Instant date;
}
