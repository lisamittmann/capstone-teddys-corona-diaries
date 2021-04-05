import styled from 'styled-components/macro'
import {Link} from "react-router-dom";
import {css} from "styled-components";

export default function Homepage() {
    return (
        <HomeLayout>
            <h1>Teddys Corona Diaries</h1>
            <HomeButton to="/recipes">REZEPTE</HomeButton>
            <HomeButton to="/coronadetails">AKTUELLE LAGE</HomeButton>
            <HomeButton to="/">LOGIN</HomeButton>
            <ExternalLink href="https://drive.google.com/drive/folders/16O8ecfl5gzHCjY4jLVWMUXsUTDzN84dm?usp=sharing" target="_blank">HOL DIR DAS EBOOK</ExternalLink>
        </HomeLayout>
    )
}

const HomeLayout = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
  background-image: url(https://storage.googleapis.com/teddys-corona-diaries-images/teddy-frontcover.jpg);
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  height: 100vh;
  gap: 20px;

  h1 {
    font-family: Space Mono;
    margin-top: 50px;
    font-size: 40px;
    font-weight: lighter;
    @media (min-width: 550px) {
      font-size: 40px;
    }
    @media (max-width: 549px) {
      font-size: 28px;
    }
  }
`

const buttonStyle = css`
  background: var(--color-turquoise-green);
  text-decoration: none;
  color: var(--color-cultured);
  font-size: 14px;
  width: 45%;
  text-align: center;
  padding-top: 14px;
  padding-bottom: 14px;
  box-shadow: 2.5px 2.5px 2.5px var(--color-battleship-grey);
  border-radius: 3px;
  font-family: Roboto;
  font-weight: bold;
  
  :visited {
    color: var(--color-cultured);
  }
`

const HomeButton = styled(Link)`${buttonStyle}`;

const ExternalLink = styled.a`${buttonStyle}`;