import styled from 'styled-components/macro'
import {useEffect, useState} from "react";
import {getProvinceDetails} from "../../service/covidApiService";
import {getColorCode} from "../../service/incidenceColorService";

export default function CoronaProvinceCard({province}) {

    const [coronaDetails, setCoronaDetails] = useState()

    useEffect(() => {
        getProvinceDetails(province)
            .then(setCoronaDetails)
    }, [province])


    return (
        <div data-testid="provinceCard">
            <ProvinceName>{province}</ProvinceName>
            {coronaDetails &&
            <CoronaCard>
                <Rectangle coronaColor={getColorCode(coronaDetails.incidenceLevel)}/>
                <DetailsList>
                    <li key={coronaDetails.incidenceValue}>
                        <span>7-Tage Inzidenzwert: </span>
                        {coronaDetails.incidenceValue}
                    </li>
                    <li key={coronaDetails.totalCases}>
                        <span>Fallzahl gesamt bisher: </span>
                        {coronaDetails.totalCases}
                    </li>
                </DetailsList>
            </CoronaCard>}
        </div>
    )
}

const ProvinceName = styled.p`
  font-size: 18px;
  font-weight: bold;
`

const CoronaCard = styled.section`
  display: grid;
  grid-template-columns: auto 1fr;
  align-items: center;
  font-size: var(--standard-font-size);
  background: var(--color-cultured);
  margin-top: 25px;
  box-shadow: 2.5px 2.5px 2.5px var(--color-silver);
  border-radius: 5px;
  grid-gap: 10px;
`

const Rectangle = styled.span`
  display: inline-block;
  background: #94CBB1;
  width: 15px;
  height: 70%;
  margin-left: 16px;
  box-shadow: 1px 1px 1px var(--color-silver);
  background: ${props => props.coronaColor}
`

const DetailsList = styled.ul`
  list-style: none;
  font-size: 14px;

  span {
    font-weight: bold;
  }

  li + li {
    padding-top: 12px;
  }
`