import styled from 'styled-components/macro'
import {Link} from "react-router-dom";
import {AiOutlineCloseCircle} from 'react-icons/ai';

export default function RecipeImage({from, imageUrl}) {
    return (
        <ImageHeader imageUrl={imageUrl}>
            <CloseButton to={from ? `/${from}` : '/recipes'}><AiOutlineCloseCircle/></CloseButton>
        </ImageHeader>
    )
}

const ImageHeader = styled.div`
  position: relative;
  background-image: ${props => `url(${props.imageUrl})`};
  padding-top: 66%;
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
`

const CloseButton = styled(Link)`
  position: absolute;
  top: 15px;
  right: 5px;
  border-radius: 50%;
  background-color: var(--color-cultured);
  width: 35px;
  height: 35px;
  justify-content: center;
  align-items: center;
  display: flex;
`
