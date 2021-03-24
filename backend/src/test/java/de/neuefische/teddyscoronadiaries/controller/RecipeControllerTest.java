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
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RecipeControllerTest {

    @LocalServerPort
    private int serverPort;

    private String getUrl(){
        return "http://localhost:" + serverPort + "api/recipe";
    }

    @Autowired
    private RecipeMongoDb recipeMongoDb;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setUp(){
        recipeMongoDb.deleteAll();
    }

    @Test
    @DisplayName("Get Recipe should return recipe")
    public void getRecipeShouldReturnRecipe(){
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
        ResponseEntity<Recipe> response = restTemplate.getForEntity(getUrl() + "/" + recipeId, Recipe.class);

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
    public void getRecipeShouldReturnErrorForNonExistingRecipe(){
        // Given
        String recipeId = "9999";

        // When
        ResponseEntity<Void> response = restTemplate.getForEntity(getUrl() + "/" + recipeId, Void.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }


}