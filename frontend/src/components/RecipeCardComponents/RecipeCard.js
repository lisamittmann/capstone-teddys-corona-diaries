import styled from 'styled-components/macro'
import {Link} from "react-router-dom"
import RecipeCardImage from "./RecipeCardImage";

export default function RecipeCard({recipeCardDetails}) {
    return (
        <CardWrapper to={`/recipe/${recipeCardDetails.id}`}>
            <p>{recipeCardDetails.name}</p>
            <RecipeCardImage imageUrl={recipeCardDetails.imageUrl}/>
        </CardWrapper>
    )
}

const CardWrapper = styled(Link)`
  display: grid;
  grid-template-rows: auto 1fr;
  vertical-align: middle;
  box-shadow: 0 0 2.5px var(--color-silver);
  text-decoration: none;
  color: black;

  p {
    padding-left: 12px;
    padding-right: 12px;
    height: 60px;
    font-size: 16px;
    margin: 0px;
    display: flex;
    align-items: center;
  }
  
`