import axios from "axios";

const baseUrl = "/covid"

export const getSevenDayIncidenceValue = (quarantineDay) =>
    axios.get(`${baseUrl}/${quarantineDay}`).then(response => response.data)