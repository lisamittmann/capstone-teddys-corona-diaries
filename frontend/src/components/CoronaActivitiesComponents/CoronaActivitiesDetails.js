import React from "react";
import styled from 'styled-components/macro'
import PropTypes from "prop-types";
import {withRouter} from "react-router";
import ProvinceIncidenceBubble from "./ProvinceIncidenceBubble";
import ProvinceCapitalWeather from "./ProvinceCapitalWeather";

class CoronaActivitiesDetails extends React.Component {

    static propTypes = {
        match: PropTypes.object.isRequired,
        location: PropTypes.object.isRequired,
        history: PropTypes.object.isRequired
    };

    render() {
        const {coronaDetails, province} = this.props.location.state;

        return (
            <div>
                <IncidenceWeatherBox>
                    <ProvinceIncidenceBubble province={province} incidenceLevel={coronaDetails.incidenceLevel}
                                             incidenceValue={coronaDetails.incidenceValue}/>
                    <ProvinceCapitalWeather province={province}/>
                </IncidenceWeatherBox>
            </div>)
    }
}

const IncidenceWeatherBox = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 10px;
  margin: 25px;
`

export default withRouter(CoronaActivitiesDetails)