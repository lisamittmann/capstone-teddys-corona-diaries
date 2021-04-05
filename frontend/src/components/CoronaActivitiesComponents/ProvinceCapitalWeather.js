import styled from "styled-components/macro";
import {useEffect, useState} from "react";
import {getWeatherDetails} from "../../service/weatherApiService";
import {GridPanel} from "./gridPanelCss";

export default function ProvinceCapitalWeather({province}) {

    const [weatherDetails, setWeatherDetails] = useState()

    useEffect(() => {
        getWeatherDetails(province).then(setWeatherDetails)
    }, [province])

    if(!weatherDetails) {
        return <WeatherWrapper/>;
    }

    return (
        <WeatherWrapper>
                <p>{weatherDetails.capital} ({weatherDetails.weatherState})</p>
                <img src={weatherDetails.weatherIconUrl} alt={weatherDetails.weatherState}/>
                <MinMaxTemp>
                    <p>Min: {weatherDetails.minTemperature}°</p>
                    <p>Max: {weatherDetails.maxTemperature}°</p>
                </MinMaxTemp>
        </WeatherWrapper>
    )
}

const WeatherWrapper = styled.section`
  ${GridPanel}

  img {
    width: 30%;
    height: auto;
  }
  
  p {
    font-family: Roboto;
    font-size: var(--standard-font-size);
    font-weight: bold;
    align-self: center;
  }
`

const MinMaxTemp = styled.div`
  display: flex;

  p {
    color: var (--color-battleship-grey);
    font-weight: normal;
    font-size: 14px;
  }

  p + p {
    padding-left: 16px;
  }

`