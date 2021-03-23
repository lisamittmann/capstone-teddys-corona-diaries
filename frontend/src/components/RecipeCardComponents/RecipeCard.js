import styled from 'styled-components/macro'
import {Link} from "react-router-dom"

export default function RecipeCard({recipeCardDetails}) {
    return (
        <CardWrapper to="/recipe/day14">
            <p>{recipeCardDetails.name}</p>
            <img src={recipeCardDetails.imageUrl}/>
            <span>Rezept-Beschreibung mit coolen Infos so</span>
        </CardWrapper>
    )
}

const CardWrapper = styled(Link)`
  display: grid;
  grid-template-rows: auto 1fr auto auto;
  vertical-align: middle;
  box-shadow: 0 0 2.5px var(--color-silver);
  text-decoration: none;
  color: black;

  p {
    padding-left: 12px;
    padding-right: 12px;
    height: 50px;
    font-size: 16px;
    margin: 0px;
    display: flex;
    align-items: center;
  }

  span {
    display: flex;
    align-items: center;
    color: var(--color-battleship-grey);
    font-size: 14px;
    padding: 12px 12px 12px 12px;
  }

  img {
    display: block;
    width: 100%;
    height: auto;
  }
`