package de.neuefische.teddyscoronadiaries.service;

import de.neuefische.teddyscoronadiaries.db.RecipeMongoDb;
import de.neuefische.teddyscoronadiaries.model.recipe.Recipe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
    public void getRecipeShouldReturnRecipe(){
        //Given
        String recipeId = "0001";
        when(mockRecipeMongoDb.findById("0001")).thenReturn(
                Optional.of(Recipe.builder()
                        .id("0001")
                        .recipeName("Himmerlischer Schokokuchen")
                        .imageUrl("some-image-url")
                        .diaryEntry("wenn absolut nichts mehr geht, dann ist es Zeit für Schokokuchen")
                        .build())
        );

        // When
        Optional<Recipe> result = recipeService.getRecipe(recipeId);

        // Then
        assertThat(result.get(), is(Recipe.builder()
                .id("0001")
                .recipeName("Himmerlischer Schokokuchen")
                .imageUrl("some-image-url")
                .diaryEntry("wenn absolut nichts mehr geht, dann ist es Zeit für Schokokuchen")
                .build()));
    }

    @Test
    @DisplayName("getRecipe should return empty Optional for non existing ID")
    public void getRecipeShouldReturnEmptyOptional(){
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
}