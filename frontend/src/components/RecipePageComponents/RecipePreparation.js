import styled from 'styled-components/macro'
import RecipeIngredients from "./RecipeIngredients";
import RecipeSteps from "./RecipeSteps";

export default function RecipePreparation({intendedFor, ingredients, steps}) {
    return (
        <PreparationGrid>
            <RecipeIngredients ingredients={ingredients} intendedFor={intendedFor}/>
            <RecipeSteps steps={steps}/>
        </PreparationGrid>
    )
}

const PreparationGrid = styled.section`
  display: grid;
  @media (min-width: 500px) {
    grid-template-columns: 40% 60%;
  }
  grid-gap: 16px;
  margin-left: 25px;
  @media (min-width: 500px) {
    margin-right: 41px;
  }
  @media (max-width: 499px) {
    margin-right: 25px;
  }
  font-size: 15px;

  div {
    background: var(--color-cultured);
    box-sizing: content-box;
    width: 100%;
  }

  ul {
    list-style: none;
    padding-right: 16px;
    padding-left: 16px;
  }

  ol {
    margin-right: 16px;
  }

  p {
    font-weight: bold;
  }

  li + li {
    padding-top: 10px;
  }
`