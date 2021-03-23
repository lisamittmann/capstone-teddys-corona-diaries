package de.neuefische.teddyscoronadiaries.service;

import de.neuefische.teddyscoronadiaries.db.RecipeMongoDb;
import de.neuefische.teddyscoronadiaries.model.recipe.Ingredient;
import de.neuefische.teddyscoronadiaries.model.recipe.PreparationStep;
import de.neuefische.teddyscoronadiaries.model.recipe.Recipe;
import de.neuefische.teddyscoronadiaries.model.recipe.RecipeCardDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RecipeServiceTest {

    private final RecipeMongoDb mockRecipeMongoDb = mock(RecipeMongoDb.class);
    private final RecipeService recipeService = new RecipeService(mockRecipeMongoDb);

    @Test
    @DisplayName("getRecipe should return recipe")
    public void getRecipeShouldReturnRecipe() {
        //Given
        String recipeId = "0001";
        when(mockRecipeMongoDb.findById("0001")).thenReturn(
                Optional.of(Recipe.builder()
                        .id("0001")
                        .name("Himmlischer Schokokuchen")
                        .imageUrl("some-image-url")
                        .diaryEntry("wenn absolut nichts mehr geht, dann ist es Zeit für Schokokuchen")
                        .build())
        );

        // When
        Optional<Recipe> result = recipeService.getRecipe(recipeId);

        // Then
        assertThat(result.get(), is(Recipe.builder()
                .id("0001")
                .name("Himmlischer Schokokuchen")
                .imageUrl("some-image-url")
                .diaryEntry("wenn absolut nichts mehr geht, dann ist es Zeit für Schokokuchen")
                .build()));
    }

    @Test
    @DisplayName("getRecipe should return empty Optional for non existing ID")
    public void getRecipeShouldReturnEmptyOptional() {
        //Given
        String recipeId = "0001";
        when(mockRecipeMongoDb.findById("0001")).thenReturn(
                Optional.empty()
        );

        // When
        Optional<Recipe> result = recipeService.getRecipe(recipeId);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Get recipes should return list of RecipeCardDetails")
    public void getRecipesShouldReturnCardDetails() {
        //Given
        when(mockRecipeMongoDb.findAll()).thenReturn(createRecipeList());

        // When
        List<RecipeCardDetails> result = recipeService.getRecipes();

        // Then
        assertThat(result, is(List.of(
                new RecipeCardDetails("0001", "Himmlischer Schokokuchen", "some-image-url"),
                new RecipeCardDetails("0002","Tofu im Reisflakemantel", "some-image-url2")
        )));

    }


    private List<Recipe> createRecipeList(){
        return List.of(
                Recipe.builder()
                        .id("0001")
                        .name("Himmlischer Schokokuchen")
                        .imageUrl("some-image-url")
                        .diaryEntry("wenn absolut nichts mehr geht, dann ist es Zeit für Schokokuchen")
                        .ingredients(List.of(
                                new Ingredient("250g", "Butter"),
                                new Ingredient("200g", "Mehl")))
                        .steps(List.of(
                                new PreparationStep("1", "Schmilz die Butter"),
                                new PreparationStep("2", "Misch Butter und Mehl")
                        ))
                        .build(),
                Recipe.builder()
                        .id("0002")
                        .name("Tofu im Reisflakemantel")
                        .imageUrl("some-image-url2")
                        .diaryEntry("hinten im Kühlschrank war noch Tofu, Zeit das der weg kommt")
                        .quarantineDay(37)
                        .ingredients(List.of(
                                new Ingredient("200g", "Tofu"),
                                new Ingredient("3-4EL", "Reisflakes")))
                        .steps(List.of(
                                new PreparationStep("1", "Tofu pressen"),
                                new PreparationStep("2", "Tofu in Reisflakes wälzen")
                        ))
                        .build()
        );
    }
}