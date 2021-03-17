import styled from 'styled-components/macro'
import {useState, useEffect} from "react";
import {useParams} from "react-router-dom";
import {getRecipe} from "../service/recipeApiService";
import RecipeHeader from "../components/RecipeHeader";
import RecipeCoronaDetails from "../components/RecipeCoronaDetails";
import RecipePreparation from "../components/RecipePreparation";


export default function RecipePage() {
    const {recipeId} = useParams()
    const [recipe, setRecipe] = useState()

    useEffect(() => {
        getRecipe(recipeId).then(setRecipe)
    }, [recipeId])

    return (
        <>
            {recipe &&
            <RecipeWrapper>
                <img src={recipe.imageUrl}/>
                <RecipeHeader recipeName={recipe.name} recipeDescription={recipe.diaryEntry}/>
                <RecipeCoronaDetails quarantineDay={recipe.quarantineDay}/>
                <RecipePreparation ingredients={recipe.ingredients} preparationSteps={recipe.steps}/>
            </RecipeWrapper>
            }
        </>
    )
}

const RecipeWrapper = styled.section`
  display: grid;
  grid-template-rows: auto auto auto auto;
  overflow-y: scroll;
  font-family: Roboto;
  grid-gap: 16px;
  margin-bottom: 16px;

  img {
    object-fit: contain;
    width: 100%;
  }


`