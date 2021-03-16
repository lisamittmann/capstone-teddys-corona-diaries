import styled from 'styled-components/macro'

export default function RecipeHeader({recipeName, recipeDescription}) {
    return(
        <Wrapper>
            <h3>{recipeName}</h3>
            <p>{recipeDescription}</p>
        </Wrapper>
    )
}

const Wrapper = styled.section`
  display: grid;
  grid-auto-rows: auto auto;

  h3 {
    padding: 10px 25px 10px 25px;
    margin: 0;
  }

  p {
    padding: 10px 25px 10px 25px;
    font-size: 16px;
    color: var(--color-battleship-grey);
    margin: 0;
  }
`