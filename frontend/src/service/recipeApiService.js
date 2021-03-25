import axios from "axios";

const baseUrl = "/api/recipe"

export const getRecipe = (recipeId) =>
    axios.get(`${baseUrl}/${recipeId}`).then(response => response.data)

export const getRecipes = () =>
    axios.get(`${baseUrl}`).then(response => response.data)