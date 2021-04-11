package de.neuefische.teddyscoronadiaries.service;

import de.neuefische.teddyscoronadiaries.db.RecipeMongoDb;
import de.neuefische.teddyscoronadiaries.db.UserSavedRecipesMongoDb;
import de.neuefische.teddyscoronadiaries.model.recipe.Recipe;
import de.neuefische.teddyscoronadiaries.model.recipe.RecipeCardDetails;
import de.neuefische.teddyscoronadiaries.model.user.UserSavedRecipes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
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

        if (savedRecipes.isEmpty()) {
            userSavedRecipesMongoDb.save(new UserSavedRecipes(userId, new ArrayList<>()));
            return "not-saved";
        }

        Optional<String> recipeStatus = savedRecipes.get().getSavedRecipeIds().stream()
                .filter(userList -> userList.equals(recipeId))
                .findAny();

        if (recipeStatus.isPresent()) {
            return "saved";
        }

        return "not-saved";
    }

    public List<RecipeCardDetails> getSavedRecipes(String userId) {

        Optional<UserSavedRecipes> savedRecipes = userSavedRecipesMongoDb.findById(userId);

        if (savedRecipes.isEmpty()) {
            userSavedRecipesMongoDb.save(new UserSavedRecipes(userId, new ArrayList<>()));
            return List.of();
        }

        return savedRecipes.get().getSavedRecipeIds().stream()
                .map(recipeMongoDb::findById)
                .map(recipeOptional -> {
                    if (recipeOptional.isPresent()) {
                        Recipe recipe = recipeOptional.get();
                        return new RecipeCardDetails(recipe.getId(), recipe.getName(), recipe.getImageUrl(), recipe.getQuarantineDay());
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public void saveRecipe(String userId, String recipeId) {

        Optional<UserSavedRecipes> savedUserRecipes = userSavedRecipesMongoDb.findById(userId);

        if (savedUserRecipes.isEmpty()) {
            userSavedRecipesMongoDb.save(new UserSavedRecipes(userId, new ArrayList<>() {{
                add(recipeId);
            }}));
        }

        if (savedUserRecipes.isPresent()) {
            ArrayList<String> savedRecipes = savedUserRecipes.get().getSavedRecipeIds();
            if (!savedRecipes.contains(recipeId)) {
                savedRecipes.add(recipeId);
            }

            userSavedRecipesMongoDb.save(savedUserRecipes.get().toBuilder()
                    .savedRecipeIds(savedRecipes)
                    .build());
        }
    }

    public String deleteRecipe(String userId, String recipeId) {

        Optional<UserSavedRecipes> savedUserRecipes = userSavedRecipesMongoDb.findById(userId);

        if(savedUserRecipes.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown user");
        }

        if (savedUserRecipes.isPresent()) {
            ArrayList<String> savedRecipes = savedUserRecipes.get().getSavedRecipeIds();

            ArrayList<String> updatedSavedRecipes = new ArrayList<>(savedRecipes.stream()
                    .filter(listItem -> !listItem.equals(recipeId))
                    .collect(Collectors.toList()));

            userSavedRecipesMongoDb.save(savedUserRecipes.get().toBuilder()
                    .savedRecipeIds(updatedSavedRecipes)
                    .build());
        }

        return recipeId;
    }

}
