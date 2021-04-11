import styled from 'styled-components/macro'
import {Link} from "react-router-dom";

export default function NavigationBar() {
    return (
        <NavigationBarWrapper>
            <NavigationLink to="/recipes">REZEPT ÜBERSICHT</NavigationLink>
            <NavigationLink to="/coronadetails">CORONA LAGE</NavigationLink>
            <NavigationLink to="/me">MEIN BEREICH</NavigationLink>
        </NavigationBarWrapper>

    )
}

const NavigationBarWrapper = styled.section`
  background: var(--color-turquoise-green);
  height: var(--navigation-bar-height);
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