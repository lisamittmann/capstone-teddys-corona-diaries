import React from "react";
import styled from 'styled-components/macro'
import {getColorCode} from "../../service/incidenceColorService";

export default function ProvinceIncidenceBubble({incidenceValue, incidenceLevel, province}) {
    return (
        <BubbleWrapper>
            <p>{province}</p>
            <Bubble coronaColor={getColorCode(incidenceLevel)}>{incidenceValue}</Bubble>
        </BubbleWrapper>
    )
}

const BubbleWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: var(--color-cultured);
  box-shadow: 2px 2px 2px var(--color-silver);
  border-radius: 3px;
  
  p {
    font-family: Roboto;
    font-size: var(--standard-font-size);
    font-weight: bold;
  }
`

const Bubble = styled.span`
  display: inline-block;
  background-color: ${props => props.coronaColor};
  border-radius: 50%;
  @media (max-width: 499px) {
    width: 100px;
    line-height: 100px;
  }
  
  @media (min-width: 500px) {
    width: 150px;
    line-height: 150px;
  }
  
  text-align: center;
  margin-bottom: 12px;
  font-family: Roboto;
  font-size: 22px;
  font-weight: bold;
`