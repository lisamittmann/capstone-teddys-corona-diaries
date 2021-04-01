import axios from "axios";

const baseUrl = "/api/weather"

export const getWeatherDetails = (province) =>
    axios.get(`${baseUrl}/${province}`).then(response => response.data)