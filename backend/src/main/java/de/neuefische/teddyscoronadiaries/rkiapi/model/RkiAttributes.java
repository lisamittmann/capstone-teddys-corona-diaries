package de.neuefische.teddyscoronadiaries.rkiapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RkiAttributes {

    @JsonProperty("attributes")
    private RkiIncidenceValue attributes;
}
