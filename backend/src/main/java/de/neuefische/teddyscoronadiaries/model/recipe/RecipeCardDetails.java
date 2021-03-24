package de.neuefische.teddyscoronadiaries.model.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeCardDetails {

    private String id;
    private String name;
    private String imageUrl;
    private int quarantineDay;

}
