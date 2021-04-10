package de.neuefische.teddyscoronadiaries.service;

import de.neuefische.teddyscoronadiaries.db.UserSavedRecipesMongoDb;
import de.neuefische.teddyscoronadiaries.model.user.UserSavedRecipes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserRecipeService {

    private final UserSavedRecipesMongoDb userSavedRecipesMongoDb;

    @Autowired
    public UserRecipeService(UserSavedRecipesMongoDb userSavedRecipesMongoDb) {
        this.userSavedRecipesMongoDb = userSavedRecipesMongoDb;
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
}
