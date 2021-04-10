import axios from "axios";

const baseUrl = "auth"

export const getClientId = () =>
    axios.get(`${baseUrl}/login/google/clientid`).then(response => response.data)

export const loginUserWithGoogle = (googleAuth) =>
    axios.post(`${baseUrl}/login/google`, googleAuth).then(response => response.data)