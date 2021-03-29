import styled from 'styled-components/macro'
import {useState, useEffect} from "react";
import {useParams} from "react-router-dom";
import {getRecipe} from "../service/recipeApiService";
import RecipeHeader from "../components/RecipePageComponents/RecipeHeader";
import RecipeCoronaDetails from "../components/RecipePageComponents/RecipeCoronaDetails";
import RecipePreparation from "../components/RecipePageComponents/RecipePreparation";
import useQuery from "../hooks/useQuery";
import RecipeImage from "../components/RecipePageComponents/RecipeImage";


export default function RecipePage() {
    const {recipeId} = useParams()
    const [recipe, setRecipe] = useState()

    const query = useQuery()
    const from = query.get('from')

    useEffect(() => {
        getRecipe(recipeId).then(setRecipe)
    }, [recipeId])

    return (
        <>
            {recipe &&
            <RecipeWrapper>
                <RecipeImage imageUrl={recipe.imageUrl} from={from}/>
                <RecipeHeader recipeName={recipe.name} recipeDescription={recipe.diaryEntry}/>
                <RecipeCoronaDetails quarantineDay={recipe.quarantineDay}/>
                <RecipePreparation {...recipe}/>
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
`