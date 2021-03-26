import styled from 'styled-components/macro'
import {getColorCode} from "../../service/incidenceColorService";

export default function IncidenceDetails({incidenceDetails}) {

    return (
        <DetailsGrid>
            <Circle coronaColor={getColorCode(incidenceDetails.incidenceLevel)}/>
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
  background: ${props => props.coronaColor}
`