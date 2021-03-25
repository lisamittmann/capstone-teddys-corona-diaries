import styled from 'styled-components/macro'
import {useState, useEffect} from "react";
import {getRecipes} from "../service/recipeApiService";
import RecipeCard from "../components/RecipeCardComponents/RecipeCard";
import PageHeader from "../components/PageHeader";

export default function RecipeOverview() {
    const [recipes, setRecipes] = useState()

    useEffect(() => {
        getRecipes().then(setRecipes)
    }, [])


    return (
        <RecipeOverviewWrapper>
            <PageHeader>Rezept Übersicht</PageHeader>
            <RecipeList>
                {recipes &&
                recipes
                    .slice()
                    .sort((oneRecipe, anotherRecipe) => oneRecipe.quarantineDay - anotherRecipe.quarantineDay)
                    .map(recipe => <RecipeCard recipeCardDetails={recipe}/>)
                }
            </RecipeList>
        </RecipeOverviewWrapper>
    )
}

const RecipeList = styled.section`
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 350px));
  grid-gap: 20px;
  font-family: Roboto;
  margin: 40px 20px 25px 20px;
  overflow-y: scroll;
`

const RecipeOverviewWrapper = styled.div`
  display: grid;
  grid-template-rows: auto 1fr;
  height: calc(100vh - 50px);
`