package de.neuefische.teddyscoronadiaries.rkiapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RkiIncidenceWrapper {

    @JsonProperty("features")
    private List<RkiAttributes> features;
}
