import React from "react";
import styled from 'styled-components/macro'
import PropTypes from "prop-types";
import {withRouter} from "react-router";
import ProvinceIncidenceBubble from "./ProvinceIncidenceBubble";
import ProvinceCapitalWeather from "./ProvinceCapitalWeather";

function CoronaActivitiesDetails({location}) {

        const {coronaDetails, province} = location.state;

        return (
            <div>
                <IncidenceWeatherBox>
                    <ProvinceIncidenceBubble province={province} incidenceLevel={coronaDetails.incidenceLevel}
                                             incidenceValue={coronaDetails.incidenceValue}/>
                    <ProvinceCapitalWeather province={province}/>
                </IncidenceWeatherBox>
            </div>)

}

export default withRouter(CoronaActivitiesDetails)

CoronaActivitiesDetails.propTypes = {
    match: PropTypes.object.isRequired,
    location: PropTypes.object.isRequired,
    history: PropTypes.object.isRequired
}

const IncidenceWeatherBox = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-gap: 10px;
  margin: 25px;
`

