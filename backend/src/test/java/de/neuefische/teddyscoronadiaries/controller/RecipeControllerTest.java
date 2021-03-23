package de.neuefische.teddyscoronadiaries.controller;

import de.neuefische.teddyscoronadiaries.db.RecipeMongoDb;
import de.neuefische.teddyscoronadiaries.model.recipe.Ingredient;
import de.neuefische.teddyscoronadiaries.model.recipe.PreparationStep;
import de.neuefische.teddyscoronadiaries.model.recipe.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RecipeControllerTest {

    @LocalServerPort
    private int serverPort;

    private String getUrl() {
        return "http://localhost:" + serverPort + "api/recipe";
    }

    @Autowired
    private RecipeMongoDb recipeMongoDb;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    public void setUp() {
        recipeMongoDb.deleteAll();
    }

    @Test
    @DisplayName("Get Recipe should return recipe")
    public void getRecipeShouldReturnRecipe() {
        //Given
        String recipeId = "0001";
        recipeMongoDb.save(Recipe.builder()
                .id("0001")
                .name("Himmlischer Schokokuchen")
                .imageUrl("some-image-url")
                .diaryEntry("wenn absolut nichts mehr geht, dann ist es Zeit für Schokokuchen")
                .quarantineDay(25)
                .ingredients(List.of(
                        new Ingredient("250g", "Butter"),
                        new Ingredient("200g", "Mehl")))
                .steps(List.of(
                        new PreparationStep("1", "Schmilz die Butter"),
                        new PreparationStep("2", "Misch Butter und Mehl")
                ))
                .build());

        // When
        ResponseEntity<Recipe> response = testRestTemplate.getForEntity(getUrl() + "/" + recipeId, Recipe.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(Recipe.builder()
                .id("0001")
                .name("Himmlischer Schokokuchen")
                .imageUrl("some-image-url")
                .diaryEntry("wenn absolut nichts mehr geht, dann ist es Zeit für Schokokuchen")
                .quarantineDay(25)
                .ingredients(List.of(
                        new Ingredient("250g", "Butter"),
                        new Ingredient("200g", "Mehl")))
                .steps(List.of(
                        new PreparationStep("1", "Schmilz die Butter"),
                        new PreparationStep("2", "Misch Butter und Mehl")
                ))
                .build()));
    }

    @Test
    @DisplayName("Get non existing recipe should return 404 error")
    public void getRecipeShouldReturnErrorForNonExistingRecipe() {
        // Given
        String recipeId = "9999";

        // When
        ResponseEntity<Void> response = testRestTemplate.getForEntity(getUrl() + "/" + recipeId, Void.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    @DisplayName("Get recipes should return all recipes in database")
    public void getRecipesShouldReturnRecipes() {
        //Given
        recipeMongoDb.save(Recipe.builder()
                .id("0001")
                .name("Himmlischer Schokokuchen")
                .imageUrl("some-image-url")
                .diaryEntry("wenn absolut nichts mehr geht, dann ist es Zeit für Schokokuchen")
                .quarantineDay(25)
                .ingredients(List.of(
                        new Ingredient("250g", "Butter"),
                        new Ingredient("200g", "Mehl")))
                .steps(List.of(
                        new PreparationStep("1", "Schmilz die Butter"),
                        new PreparationStep("2", "Misch Butter und Mehl")
                ))
                .build());
        recipeMongoDb.save(Recipe.builder()
                .id("0002")
                .name("Tofu im Reisflake-Mante")
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
                .build());

        //When
        ResponseEntity<Recipe[]> response = testRestTemplate.getForEntity(getUrl(), Recipe[].class);

        //Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), arrayContainingInAnyOrder(
                Recipe.builder()
                        .id("0001")
                        .name("Himmlischer Schokokuchen")
                        .imageUrl("some-image-url")
                        .diaryEntry("wenn absolut nichts mehr geht, dann ist es Zeit für Schokokuchen")
                        .quarantineDay(25)
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
                        .name("Tofu im Reisflake-Mante")
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
        ));
    }

    @Test
    @DisplayName("Get recipes should return empty list when mongo db is empty or not available")
    public void getRecipesShouldReturnEmptyListWhenMongoDbEmpty() {
        // When
        ResponseEntity<Recipe[]> response = testRestTemplate.getForEntity(getUrl(), Recipe[].class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(emptyArray()));
    }

}