package de.neuefische.teddyscoronadiaries.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "usersavedrecipes")
public class UserSavedRecipes {

    @Id
    private String userId;
    private List<String> savedRecipeIds;
}
