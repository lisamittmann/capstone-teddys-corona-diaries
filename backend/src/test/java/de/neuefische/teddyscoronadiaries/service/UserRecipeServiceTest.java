package de.neuefische.teddyscoronadiaries.service;

import de.neuefische.teddyscoronadiaries.db.UserSavedRecipesMongoDb;
import de.neuefische.teddyscoronadiaries.model.user.UserSavedRecipes;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserRecipeServiceTest {

    private final UserSavedRecipesMongoDb userSavedRecipesMongoDb = mock(UserSavedRecipesMongoDb.class);
    private final UserRecipeService userRecipeService = new UserRecipeService(userSavedRecipesMongoDb);

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
                Arguments.of(Optional.of(new UserSavedRecipes("awesomeUserId", List.of(
                        "awesomeRecipeId",
                        "awesomeRecipeId2",
                        "awesomeRecipeId3"
                ))), "saved"),
                Arguments.of(Optional.of(new UserSavedRecipes("awesomeUserId", List.of(
                        "awesomeRecipeId2",
                        "awesomeRecipeId3"
                ))), "not-saved"),
                Arguments.of(
                        Optional.empty(),
                        "not-saved"
                )
        );
    }


}