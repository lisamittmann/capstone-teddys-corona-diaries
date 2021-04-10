import React, {useEffect, useState} from "react";
import styled from 'styled-components/macro'
import PropTypes from "prop-types";
import {withRouter} from "react-router";
import ProvinceIncidenceBubble from "./ProvinceIncidenceBubble";
import ProvinceCapitalWeather from "./ProvinceCapitalWeather";
import {getActivities} from "../../service/activityApiService";
import ActivityCard from "./ActivityCard";

function CoronaActivitiesDetails({location}) {

    const [activities, setActivities] = useState()
    const [weatherCategory, setWeatherCategory] = useState()

    const {coronaDetails, province} = location.state;

    useEffect(() => {
        getActivities(weatherCategory, coronaDetails.incidenceLevel).then(setActivities)
    }, [weatherCategory])


    console.log(activities)

    return (
        <ActivityWrapper>
            <IncidenceWeatherBox>
                <ProvinceIncidenceBubble province={province} incidenceLevel={coronaDetails.incidenceLevel}
                                         incidenceValue={coronaDetails.incidenceValue}/>
                <ProvinceCapitalWeather province={province} setWeatherCategory={setWeatherCategory}/>
            </IncidenceWeatherBox>

            {activities &&
            activities.map(activity => <ActivityCard activity={activity}/>)
            }

        </ActivityWrapper>)

}

export default withRouter(CoronaActivitiesDetails)

const ActivityWrapper = styled.div`
  overflow-y: scroll;
  margin-bottom: 25px;
  margin-top: 25px;
  display: flex;
  flex-direction: column;
  flex-wrap: nowrap;
`

CoronaActivitiesDetails.propTypes = {
    match: PropTypes.object.isRequired,
    location: PropTypes.object.isRequired,
    history: PropTypes.object.isRequired
}

const IncidenceWeatherBox = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-gap: 10px;
  margin-left: 25px;
  margin-right: 25px;
  margin-bottom: 25px;
`

