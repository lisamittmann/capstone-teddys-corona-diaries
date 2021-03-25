package de.neuefische.teddyscoronadiaries.controller;

import de.neuefische.teddyscoronadiaries.db.RecipeMongoDb;
import de.neuefische.teddyscoronadiaries.model.recipe.Ingredient;
import de.neuefische.teddyscoronadiaries.model.recipe.PreparationStep;
import de.neuefische.teddyscoronadiaries.model.recipe.Recipe;
import de.neuefische.teddyscoronadiaries.model.recipe.RecipeCardDetails;
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
    public void setup() {
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
                .intendedFor("Für 20cm Springform")
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
                .intendedFor("Für 20cm Springform")
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
                .name("Tofu im Reisflake-Mantel")
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
        ResponseEntity<RecipeCardDetails[]> response = testRestTemplate.getForEntity(getUrl(), RecipeCardDetails[].class);

        //Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), arrayContainingInAnyOrder(
                new RecipeCardDetails("0001", "Himmlischer Schokokuchen", "some-image-url", 25),
                new RecipeCardDetails("0002", "Tofu im Reisflake-Mantel", "some-image-url2", 37)
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