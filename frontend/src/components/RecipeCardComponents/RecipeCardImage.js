import styled from 'styled-components/macro'

export default function RecipeCardImage({imageUrl}) {
    return (
        <Wrapper>
            <img src={imageUrl}/>
            <span>Tag 24</span>
        </Wrapper>
    )
}

const Wrapper = styled.div`

  img {
    width: 100%;
    position: absolute;
    height: auto;
  }
  
  span {
    position: relative;
    top: 15px; 
    background: var(--color-turquoise-green);
  }
`
