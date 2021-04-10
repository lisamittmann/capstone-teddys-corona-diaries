import { AuthContext } from './AuthContext'
import { useState, useEffect } from 'react'
import axiosConfig from "../../service/axiosConfig";

export default function AuthProvider({ children }) {
    const [token, setToken] = useState('')

    useEffect(() => {
        if (token) {
            axiosConfig.setAxiosAuthToken(token)
        }
    }, [token])

    return (
        <AuthContext.Provider value={{ token, setToken }}>
            {children}
        </AuthContext.Provider>
    )
}