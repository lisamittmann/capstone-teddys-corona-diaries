import axios from "axios";

const baseUrl = "/api/activity"

export const getActivities = (weatherCategory, incidenceLevel) =>
    axios.get(`${baseUrl}/${weatherCategory}/${incidenceLevel}`).then(response => response.data)