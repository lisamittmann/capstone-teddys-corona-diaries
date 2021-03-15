package de.neuefische.teddyscoronadiaries.db;

import de.neuefische.teddyscoronadiaries.model.recipe.Recipe;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RecipeMongoDb extends PagingAndSortingRepository<Recipe, String> {

    List<Recipe> findAll();
}
