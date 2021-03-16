import styled from 'styled-components/macro'

export default function RecipePreparation({ingredients, preparationSteps}) {
    return (
        <PreparationGrid>
            <div>
                <ul>
                    <p>Zutaten</p>
                    {
                        ingredients.map(ingredient => (
                            <li key={ingredient.name}>{ingredient.amountAndUnit} {ingredient.name}</li>))
                    }
                </ul>
            </div>
            <div>
                <ol>
                    <p>Zubereitung</p>
                    {
                        preparationSteps.sort((oneStep, anotherStep) => oneStep.stepNumber.localeCompare(anotherStep.stepNumber))
                            .map(step => (
                                <li>{step.stepDescription}</li>
                            ))
                    }
                </ol>
            </div>
        </PreparationGrid>
    )
}

const PreparationGrid = styled.section`
  display: grid;
  grid-template-columns: 30% 70%;
  grid-gap: 16px;
  margin-left: 25px;
  margin-right: 41px;
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