import styled from 'styled-components/macro'
import {useState,useEffect} from "react";
import {getRecipes} from "../service/recipeApiService";
import RecipeCard from "../components/RecipeCardComponents/RecipeCard";

export default function RecipeOverview() {
    const[recipes, setRecipes] = useState()

    useEffect(() => {
        getRecipes().then(setRecipes)
    }, [])


    return (
        <RecipeList>
            {recipes &&
                recipes.map(recipe => <RecipeCard recipeCardDetails={recipe}/>)
            }
        </RecipeList>
    )
}

const RecipeList = styled.section`
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 350px));
  grid-gap: 25px;
  font-family: Roboto;
  margin: 25px;
`
