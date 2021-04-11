import {useEffect, useState} from "react";
import {getClientId} from "../../service/googleAuthService";
import {GoogleLogout} from "react-google-login";
import {useHistory} from "react-router";
import {useAuth} from "./AuthContext";

export default function LogoutWithGoogle(){
    const{setToken} = useAuth()
    const [clientId, setClientId] = useState()
    const history = useHistory()

    useEffect(() => {
        getClientId().then(setClientId)
    }, [])

    const onSuccess = () => {
        setToken('')
        history.push("/login")
    }


    return (
        <div>
            {clientId &&
            <GoogleLogout
                clientId={clientId}
                buttonText="Logout"
                onLogoutSuccess={onSuccess}
            />}
        </div>
    )
}