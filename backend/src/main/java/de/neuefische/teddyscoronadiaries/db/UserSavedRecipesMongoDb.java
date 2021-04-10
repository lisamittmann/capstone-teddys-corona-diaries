package de.neuefische.teddyscoronadiaries.db;

import de.neuefische.teddyscoronadiaries.model.user.UserSavedRecipes;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserSavedRecipesMongoDb extends PagingAndSortingRepository<UserSavedRecipes, String> {
}
