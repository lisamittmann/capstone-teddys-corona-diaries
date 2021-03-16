package de.neuefische.teddyscoronadiaries.model.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreparationStep {
    private String stepNumber;
    private String stepDescription;
}
