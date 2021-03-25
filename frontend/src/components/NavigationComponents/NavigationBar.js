import styled from 'styled-components/macro'
import {Link} from "react-router-dom";

export default function NavigationBar() {
    return (
        <NavigationWrapper>
            <NavigationLink to="/recipes">REZEPT ÜBERSICHT</NavigationLink>
            <NavigationLink to="/">CORONA LAGE</NavigationLink>
            <NavigationLink to="/">MEIN BEREICH</NavigationLink>
        </NavigationWrapper>

    )
}

const NavigationWrapper = styled.section`
  background: var(--color-turquoise-green);
  height: 50px;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  grid-gap: 12px;
  align-items: center;
  padding-left: 12px;
  padding-right: 12px;
`

const NavigationLink = styled(Link)`
  text-decoration: none;
  color: var(--color-cultured);
  text-align: center;
  font-family: Roboto;
  font-weight: bold;
  font-size: 14px;
`