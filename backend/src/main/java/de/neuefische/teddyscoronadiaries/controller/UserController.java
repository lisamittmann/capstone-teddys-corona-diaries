package de.neuefische.teddyscoronadiaries.controller;

import de.neuefische.teddyscoronadiaries.model.recipe.RecipeCardDetails;
import de.neuefische.teddyscoronadiaries.service.UserRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("auth/user")
public class UserController {

    private final UserRecipeService userRecipeService;

    @Autowired
    public UserController(UserRecipeService userRecipeService) {
        this.userRecipeService = userRecipeService;
    }

    @GetMapping("recipe/{recipeId}")
    public String getRecipeStatus(@PathVariable String recipeId, Principal principal) {
        return userRecipeService.getRecipeStatus(principal.getName(), recipeId);
    }

    @GetMapping("recipes")
    public List<RecipeCardDetails> getSavedRecipes(Principal principal) {
        return userRecipeService.getSavedRecipes(principal.getName());
    }
}
