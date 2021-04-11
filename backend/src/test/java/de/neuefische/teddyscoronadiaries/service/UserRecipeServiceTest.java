package de.neuefische.teddyscoronadiaries.service;

import de.neuefische.teddyscoronadiaries.db.RecipeMongoDb;
import de.neuefische.teddyscoronadiaries.db.UserSavedRecipesMongoDb;
import de.neuefische.teddyscoronadiaries.model.recipe.Recipe;
import de.neuefische.teddyscoronadiaries.model.recipe.RecipeCardDetails;
import de.neuefische.teddyscoronadiaries.model.user.UserSavedRecipes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

class UserRecipeServiceTest {

    private final UserSavedRecipesMongoDb userSavedRecipesMongoDb = mock(UserSavedRecipesMongoDb.class);
    private final RecipeMongoDb recipeMongoDb = mock(RecipeMongoDb.class);
    private final UserRecipeService userRecipeService = new UserRecipeService(userSavedRecipesMongoDb, recipeMongoDb);

    @ParameterizedTest
    @MethodSource("getSavedRecipes")
    @DisplayName("Get recipe status should return recipe status")
    public void getRecipeStatusShouldReturnRecipeStatus(Optional<UserSavedRecipes> mockResponse, String expected) {
        // Given
        String userId = "awesomeUserId";
        String recipeId = "awesomeRecipeId";

        when(userSavedRecipesMongoDb.findById(userId)).thenReturn(mockResponse);

        // When
        String result = userRecipeService.getRecipeStatus(userId, recipeId);

        // Then
        assertThat(result, is(expected));
    }

    private static Stream<Arguments> getSavedRecipes() {
        return Stream.of(
                Arguments.of(Optional.of(new UserSavedRecipes("awesomeUserId",
                        new ArrayList<>() {{
                            add("awesomeRecipeId");
                            add("awesomeRecipeId2");
                            add("awesomeRecipeId3");
                        }})), "saved",
                Arguments.of(Optional.of(new UserSavedRecipes("awesomeUserId", new ArrayList<>() {{
                    add("awesomeRecipeId2");
                    add("awesomeRecipeId3");
                }}
                ))), "not-saved"),
                Arguments.of(
                        Optional.empty(),
                        "not-saved"
                )
        );
    }

    @Test
    @DisplayName("Get saved recipes should return list of recipes")
    public void getSavedRecipesShouldReturnListOfRecipes() {
        // Given
        String userId = "awesomeUserId";

        when(userSavedRecipesMongoDb.findById(userId)).thenReturn(Optional.of(new UserSavedRecipes("awesomeUserId",
                new ArrayList<>() {{
                    add("recipe1");
                    add("recipe2");
                    add("recipe3");
                }}
        )));

        when(recipeMongoDb.findById("recipe1")).thenReturn(Optional.of(getRecipe("1")));
        when(recipeMongoDb.findById("recipe2")).thenReturn(Optional.of(getRecipe("2")));
        when(recipeMongoDb.findById("recipe3")).thenReturn(Optional.empty());

        // When
        List<RecipeCardDetails> savedRecipes = userRecipeService.getSavedRecipes(userId);

        // Then
        assertThat(savedRecipes, is(List.of(
                new RecipeCardDetails("1", "awesomeName1", "imageUrl1", 12),
                new RecipeCardDetails("2", "awesomeName2", "imageUrl2", 12)
        )));


    }

    @Test
    @DisplayName("Save recipe should update user saved recipes DB")
    public void saveRecipeShouldUpdateListOfSavedRecipes() {
        // Given
        String userId = "awesomeUserId";
        String recipeId = "awesomeRecipeId";

        when(userSavedRecipesMongoDb.findById(userId)).thenReturn(Optional.of(
                new UserSavedRecipes("awesomeUserId", new ArrayList<>() {{add("recipe1"); add("recipe2");}}
        )));

        // When
        userRecipeService.saveRecipe(userId, recipeId);

        // Then
        verify(userSavedRecipesMongoDb).save(
                new UserSavedRecipes(userId, new ArrayList<>() {{add("recipe1"); add("recipe2"); add(recipeId);}}
        ));

    }

    @Test
    @DisplayName("Save recipe should not save duplicate recipe")
    public void saveRecipeShouldNotSaveDuplicateRecipe() {
        // Given
        String userId = "awesomeUserId";
        String recipeId = "awesomeRecipeId";

        when(userSavedRecipesMongoDb.findById(userId)).thenReturn(Optional.of(
                new UserSavedRecipes("awesomeUserId", new ArrayList<>() {{add("recipe1"); add(recipeId);}}
                )));

        // When
        userRecipeService.saveRecipe(userId, recipeId);

        // Then
        verify(userSavedRecipesMongoDb).save(
                new UserSavedRecipes(userId, new ArrayList<>() {{add("recipe1"); add(recipeId);}}
                ));
    }

    private Recipe getRecipe(String id) {
        return Recipe.builder()
                .id(id)
                .name("awesomeName" + id)
                .imageUrl("imageUrl" + id)
                .diaryEntry("diaryEntry" + id)
                .intendedFor("intendedFor" + id)
                .quarantineDay(12)
                .build();

    }


}