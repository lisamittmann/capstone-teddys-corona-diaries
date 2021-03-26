import axios from "axios";

const baseUrl = "/api/covid"

export const getSevenDayIncidenceValue = (quarantineDay) =>
    axios.get(`${baseUrl}/${quarantineDay}`).then(response => response.data)

export const getProvinces = () =>
    axios.get(`${baseUrl}/provinces`).then(response => response.data)

export const getProvinceDetails = (province) =>
    axios.get(`${baseUrl}/province/${province}`).then(response => response.data)