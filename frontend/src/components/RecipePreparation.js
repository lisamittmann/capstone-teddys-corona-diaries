import styled from 'styled-components/macro'

export default function RecipePreparation({ingredients, preparationSteps}) {
    return (
        <PreparationGrid>
            <div>
                <ul>
                    <p>Zutaten</p>
                    {ingredients.map(ingredient => (
                            <li key={ingredient.amountAndUnit + ingredient.name}>{ingredient.amountAndUnit} {ingredient.name}</li>))
                    }
                </ul>
            </div>
            <div>
                <ol>
                    <p>Zubereitung</p>
                    {preparationSteps.sort((oneStep, anotherStep) => oneStep.stepNumber.localeCompare(anotherStep.stepNumber))
                            .map(step => (
                                <li key={step.stepNumber}>{step.stepDescription}</li>))
                    }
                </ol>
            </div>
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
  @media (max-width: 499px){
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
    padding-right: var(--medium-padding);
    padding-left: var(--medium-padding);
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