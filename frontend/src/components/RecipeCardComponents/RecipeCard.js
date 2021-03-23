import styled from 'styled-components/macro'

export default function RecipeCard({recipeCardDetails}) {
    return (
        <CardWrapper>
            <p>Himmlischer Schokokuchen</p>
            <img/>
            <p>Rezept-Beschreibung</p>
        </CardWrapper>
    )
}

const CardWrapper = styled.div`
  display: grid;
  grid-template-rows: auto 1fr auto auto;
`