package de.neuefische.teddyscoronadiaries.model.covid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncidenceDetails {

    private int incidenceValue;
    private IncidenceLevel incidenceLevel;
}
