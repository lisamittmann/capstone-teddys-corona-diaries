package de.neuefische.teddyscoronadiaries.service;

import de.neuefische.teddyscoronadiaries.db.RecipeMongoDb;
import de.neuefische.teddyscoronadiaries.model.recipe.Recipe;
import de.neuefische.teddyscoronadiaries.model.recipe.RecipeCardDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final RecipeMongoDb recipeMongoDb;

    @Autowired
    public RecipeService(RecipeMongoDb recipeMongoDb) {
        this.recipeMongoDb = recipeMongoDb;
    }

    public Optional<Recipe> getRecipe(String recipeId) {
        return recipeMongoDb.findById(recipeId);
    }

    public List<RecipeCardDetails> getRecipes() {
        return recipeMongoDb.findAll().stream()
                .map(recipe -> new RecipeCardDetails(recipe.getId(), recipe.getName(), recipe.getImageUrl(), recipe.getQuarantineDay()))
                .collect(Collectors.toList());
    }
}
