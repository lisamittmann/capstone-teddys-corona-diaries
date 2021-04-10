import styled from "styled-components/macro";
import {useEffect, useState} from "react";
import {getWeatherDetails} from "../../service/weatherApiService";
import {GridPanel} from "./gridPanelCss";

export default function ProvinceCapitalWeather({province, setWeatherCategory}) {

    const [weatherDetails, setWeatherDetails] = useState()

    useEffect(() => {
        getWeatherDetails(province).then(setWeatherDetails)
    }, [province])

    if(weatherDetails) {
        setWeatherCategory(weatherDetails.weatherCategory)
    }

    if (!weatherDetails) {
        return <WeatherWrapper/>;
    }

    return (
        <WeatherWrapper>
            <Headline>
                <p>{weatherDetails.capital}</p>
                <p>{weatherDetails.weatherState}</p>
            </Headline>
            <CurrentWeather>
                <img src={weatherDetails.weatherIconUrl} alt={weatherDetails.weatherState}/>
                <p>{weatherDetails.currentTemperature}°</p>
            </CurrentWeather>
            <MinMaxTemp>
                <p>Min: {weatherDetails.minTemperature}°</p>
                <p>Max: {weatherDetails.maxTemperature}°</p>
            </MinMaxTemp>
        </WeatherWrapper>
    )
}

const WeatherWrapper = styled.section`
  ${GridPanel}
  p {
    font-family: Roboto;
    font-size: var(--standard-font-size);
    font-weight: bold;
    align-self: center;
  }
`

const Headline = styled.section`
  text-align: center;
  p {
    padding: 0px 12px;
  }
  p + p {
  margin-top: 0px;
    margin-bottom: 5px;
  }
`

const CurrentWeather = styled.section`
  display: flex;
  flex-flow: row;
  flex-wrap: nowrap;
  justify-content: center;
  align-items: center;
  
  p {
    font-size: 24px;
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