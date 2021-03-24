import styled from 'styled-components/macro'
import {Link} from "react-router-dom"
import RecipeCardImage from "./RecipeCardImage";

export default function RecipeCard({recipeCardDetails}) {
    return (
        <CardWrapper to={`/recipe/${recipeCardDetails.id}`} key={recipeCardDetails.id}>
            <p>{recipeCardDetails.name}</p>
            <RecipeCardImage imageUrl={recipeCardDetails.imageUrl} quarantineDay={recipeCardDetails.quarantineDay}/>
        </CardWrapper>
    )
}

const CardWrapper = styled(Link)`
  display: grid;
  grid-template-rows: auto auto;
  box-shadow: 0 0 2.5px var(--color-silver);
  text-decoration: none;
  color: black;
  margin: 2.5px;

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