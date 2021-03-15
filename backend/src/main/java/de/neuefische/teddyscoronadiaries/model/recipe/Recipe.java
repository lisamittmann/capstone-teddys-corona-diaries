package de.neuefische.teddyscoronadiaries.model.recipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recipe {

    private String id;
    private String recipeName;
    private String imageUrl;
    private String diaryEntry;
    private List<Ingredient> ingredients;
    private List<PreparationStep> steps;
    private int quarantineDay;

}
