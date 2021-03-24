import styled from 'styled-components/macro'

export default function RecipeSteps({steps}) {
    return (
        <StepsWrapper>
            <p>Zubereitung</p>
            <ol>
                {steps.slice().sort((oneStep, anotherStep) => oneStep.stepNumber.localeCompare(anotherStep.stepNumber))
                    .map(step => (
                        <li key={step.stepNumber}>{step.stepDescription}</li>))
                }
            </ol>
        </StepsWrapper>
    )
}

const StepsWrapper = styled.div`
  p {
    padding-left: 24px;
  }
`