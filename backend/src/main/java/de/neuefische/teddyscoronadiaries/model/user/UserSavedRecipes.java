package de.neuefische.teddyscoronadiaries.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "usersavedrecipes")
public class UserSavedRecipes {

    @Id
    private String userId;
    private ArrayList<String> savedRecipeIds;
}
