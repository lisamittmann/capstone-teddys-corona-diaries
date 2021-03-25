import styled from 'styled-components/macro'

export default function RecipeIngredients({ingredients, intendedFor}) {
    return (
        <IngredientWrapper>
            <p>{intendedFor}</p>
            <ul>
                {ingredients.map(ingredient => (
                    <li key={ingredient.amountAndUnit + ingredient.name}>{ingredient.amountAndUnit} {ingredient.name}</li>))
                }
            </ul>
        </IngredientWrapper>
    )
}

const IngredientWrapper = styled.div`
  p {
    padding-left: 16px;
  }
`