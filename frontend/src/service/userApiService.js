import axiosConfig from "./axiosConfig";

const baseUrl = "/auth/user"

export const getSavedRecipes = () =>
    axiosConfig.axiosInstance.get(`${baseUrl}/recipes`).then(response => response.data)

export const getRecipeStatus = (recipeId) =>
    axiosConfig.axiosInstance.get(`${baseUrl}/recipe/${recipeId}`).then(response => response.data)

export const postSaveRecipe = (recipeId) =>
    axiosConfig.axiosInstance.post(`${baseUrl}/recipe`, {recipeId}).then(response => response.data)

export const deleteRecipe = (recipeId) =>
    axiosConfig.axiosInstance.delete(`${baseUrl}/recipe/${recipeId}`).then(response => response.data)