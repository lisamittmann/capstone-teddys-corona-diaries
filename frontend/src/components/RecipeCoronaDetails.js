import styled from 'styled-components/macro'
import {useState, useEffect} from "react";
import {getSevenDayIncidenceValue} from "../service/covidApiService";
import IncidenceDetails from "./IncidenceDetails";

export default function RecipeCoronaDetails({quarantineDay}) {
    const [incidenceDetails, setIncidenceDetails] = useState("Keine Ahnung")

    useEffect(() => {
        getSevenDayIncidenceValue(quarantineDay).then(setIncidenceDetails)
    }, [quarantineDay])

    return (
        <CoronaBox>
            <p>Quarantäne Tag: {quarantineDay}</p>
            <IncidenceDetails incidenceDetails={incidenceDetails}/>
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
    p:nth-child(2) {
      margin-top: 0px;
    }
    span {
      margin-bottom: 16px;
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