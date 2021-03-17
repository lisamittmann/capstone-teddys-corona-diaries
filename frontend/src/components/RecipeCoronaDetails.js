import styled from 'styled-components/macro'
import {useState, useEffect} from "react";
import {getSevenDayIncidenceValue} from "../service/covidApiService";

export default function RecipeCoronaDetails({quarantineDay}) {
    const [incidence, setIncidence] = useState("Keine Ahnung")

    useEffect(() => {
        getSevenDayIncidenceValue(quarantineDay).then(setIncidence)
    }, [quarantineDay])

    return (
        <CoronaBox>
            <p>Quarantäne Tag: {quarantineDay}</p>
            <p>7-Tage Inzidenz Wert: {incidence}</p>
        </CoronaBox>
    )
}

const CoronaBox = styled.section`
  display: grid;
  grid-template-columns: 30% 70%;
  height: 45px;
  font-size: 16px;
  background: var(--color-alabaster);
  margin-left: 25px;
  margin-right: 25px;
  align-items: center;
  grid-gap: 10px;

  p {
    padding-left: 16px;
  }
`