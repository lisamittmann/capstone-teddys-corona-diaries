import styled from 'styled-components/macro'
import {useState, useEffect} from "react";
import {useParams} from "react-router-dom";
import {getRecipe} from "../service/recipeApiService";


export default function RecipePage(){
    const {recipeId} = useParams()

    const [recipe, setRecipe] = useState()

    useEffect(() => {
        getRecipe(recipeId).then(setRecipe)
    }, [])


    return (
        <section>
            {recipe &&
            <p>{recipe.name}</p>
            }
        </section>
    )
}

const RecipeWrapper = styled.section`
  display: grid;
  grid-template-rows: repeat(4, auto);
`