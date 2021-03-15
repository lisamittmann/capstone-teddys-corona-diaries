package de.neuefische.teddyscoronadiaries.model.recipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "recipes")
public class Recipe {

    @Id
    private String id;
    private String name;
    private String imageUrl;
    private String diaryEntry;
    private List<Ingredient> ingredients;
    private List<PreparationStep> steps;
    private int quarantineDay;

}
