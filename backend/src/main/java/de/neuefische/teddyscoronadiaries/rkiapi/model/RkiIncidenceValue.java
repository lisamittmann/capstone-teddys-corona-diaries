package de.neuefische.teddyscoronadiaries.rkiapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RkiIncidenceValue {
    @JsonProperty("LAN_ew_GEN")
    private String province;
    @JsonProperty("Fallzahl")
    private int totalCases;
    @JsonProperty("cases7_bl_per_100k")
    private double sevenDayIncidenceValue;
}
