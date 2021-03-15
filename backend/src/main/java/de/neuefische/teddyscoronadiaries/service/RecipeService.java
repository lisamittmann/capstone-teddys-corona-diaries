package de.neuefische.teddyscoronadiaries.service;

import de.neuefische.teddyscoronadiaries.db.RecipeMongoDb;
import org.springframework.beans.factory.annotation.Autowired;

public class RecipeService {

    private final RecipeMongoDb recipeMongoDb;

    @Autowired
    public RecipeService(RecipeMongoDb recipeMongoDb) {
        this.recipeMongoDb = recipeMongoDb;
    }

}
