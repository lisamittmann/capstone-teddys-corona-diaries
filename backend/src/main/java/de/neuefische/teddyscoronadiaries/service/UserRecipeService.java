package de.neuefische.teddyscoronadiaries.service;

import de.neuefische.teddyscoronadiaries.db.RecipeMongoDb;
import de.neuefische.teddyscoronadiaries.db.UserSavedRecipesMongoDb;
import de.neuefische.teddyscoronadiaries.model.recipe.Recipe;
import de.neuefische.teddyscoronadiaries.model.recipe.RecipeCardDetails;
import de.neuefische.teddyscoronadiaries.model.user.UserSavedRecipes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserRecipeService {

    private final UserSavedRecipesMongoDb userSavedRecipesMongoDb;
    private final RecipeMongoDb recipeMongoDb;

    @Autowired
    public UserRecipeService(UserSavedRecipesMongoDb userSavedRecipesMongoDb, RecipeMongoDb recipeMongoDb) {
        this.userSavedRecipesMongoDb = userSavedRecipesMongoDb;
        this.recipeMongoDb = recipeMongoDb;
    }

    public String getRecipeStatus(String userId, String recipeId) {

        Optional<UserSavedRecipes> savedRecipes = userSavedRecipesMongoDb.findById(userId);

        if(savedRecipes.isEmpty()) {
            userSavedRecipesMongoDb.save(new UserSavedRecipes(userId, List.of()));
            return "not-saved";
        }

        Optional<String> recipeStatus = savedRecipes.get().getSavedRecipeIds().stream()
                .filter(userList -> userList.equals(recipeId))
                .findAny();

        if(recipeStatus.isPresent()) {
            return "saved";
        }

        return "not-saved";
    }

    public List<RecipeCardDetails> getSavedRecipes(String userId) {

        Optional<UserSavedRecipes> savedRecipes = userSavedRecipesMongoDb.findById(userId);

        if(savedRecipes.isEmpty()) {
            userSavedRecipesMongoDb.save(new UserSavedRecipes(userId, List.of()));
            return List.of();
        }

        List<RecipeCardDetails> recipeList = savedRecipes.get().getSavedRecipeIds().stream()
                .map(recipeId -> recipeMongoDb.findById(recipeId))
                .map(recipeOptional -> {
                    if (recipeOptional.isPresent()) {
                        Recipe recipe = recipeOptional.get();
                        return new RecipeCardDetails(recipe.getId(), recipe.getName(), recipe.getImageUrl(), recipe.getQuarantineDay());
                    } else {
                        return null;
                    }
                })
                .filter(recipeCardDetails -> recipeCardDetails != null)
                .collect(Collectors.toList());

        return recipeList;

    }

}
