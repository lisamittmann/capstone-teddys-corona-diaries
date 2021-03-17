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
  
  @media (min-width: 500px) {
    grid-template-columns: 40% 60%;
    grid-gap: 10px;
  }
  
  @media (max-width: 499px) {
    grid-auto-rows: auto;
    p + p {
      margin-top: 0px;
    }
  }
  
  font-size: 16px;
  background: var(--color-alabaster);
  margin-left: 25px;
  margin-right: 25px;
  align-items: center;
  
  p {
    padding-left: 16px;
  }
`