import styled from 'styled-components/macro'

export default function RecipeCardImage({imageUrl, quarantineDay}) {
    return (
        <Wrapper imageUrl={imageUrl}>
            <span>Tag {quarantineDay}</span>
        </Wrapper>
    )
}

const Wrapper = styled.div`
  position: relative;
  background-image: ${props => `url(${props.imageUrl})`};
  padding-top: 66%;
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  
  span {
    position: absolute;
    top: 15px; 
    left: 0;
    background: var(--color-cambridge-blue);
    width: 15%;
    padding: 5px 12px 5px 12px;
    font-size: 12px;
  }
`
