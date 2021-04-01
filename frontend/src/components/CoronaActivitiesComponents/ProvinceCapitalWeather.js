import styled from "styled-components/macro";
import {useEffect, useState} from "react";
import {getWeatherDetails} from "../../service/weatherApiService";

export default function ProvinceCapitalWeather({province}) {

    const [weatherDetails, setWeatherDetails] = useState()

    useEffect(() => {
        getWeatherDetails(province).then(setWeatherDetails)
    }, [province])

    return (
        <WeatherWrapper>
            {weatherDetails &&
            <>
                <p>{weatherDetails.capital} ({weatherDetails.weatherState})</p>
                <img src={weatherDetails.weatherIconUrl}/>
                <MinMaxTemp>
                    <p>Min: {weatherDetails.minTemperature}°</p>
                    <p>Max: {weatherDetails.maxTemperature}°</p>
                </MinMaxTemp>
            </>
            }
        </WeatherWrapper>
    )
}

const WeatherWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: var(--color-cultured);
  box-shadow: 2px 2px 2px var(--color-silver);
  border-radius: 3px;

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