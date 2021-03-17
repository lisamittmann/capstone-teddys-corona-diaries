import styled from 'styled-components/macro'
import {ThemeProvider} from "styled-components";

export default function IncidenceDetails({incidenceDetails}) {

    let theme

    switch (incidenceDetails.incidenceLevel) {
        case "GREEN":
            theme = {main: "var(--color-incidence-green)"}
            break
        case "YELLOW":
            theme = {main: "var(--color-incidence-yellow)"}
            break
        case "ORANGE":
            theme = {main: "var(--color-incidence-orange)"}
            break
        case "RED":
            theme = {main: "var(--color-incidence-red)"}
            break
        default:
            theme = {main: "var(--color-silver)"}
    }


    return (
        <DetailsGrid>
            <ThemeProvider theme={theme}>
                <Circle/>
            </ThemeProvider>
            <p>7-Tage Inzidenz Wert: {incidenceDetails.incidenceValue}</p>
        </DetailsGrid>
    )

}

const DetailsGrid = styled.div`
  display: grid;
  align-items: center;
  grid-template-columns: auto 1fr;
`

const Circle = styled.span`
  border-radius: 50%;
  background: #94CBB1;
  width: 25px;
  height: 25px;
  margin-left: 16px;
  box-shadow: 1px 1px 1px var(--color-silver);
  background: ${props => props.theme.main}
`