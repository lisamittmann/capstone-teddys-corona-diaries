import {useEffect, useState} from "react";
import {getClientId} from "../../service/googleAuthService";
import {GoogleLogout} from "react-google-login";

export default function LogoutWithGoogle({setImageUrl}){
    const [clientId, setClientId] = useState()

    useEffect(() => {
        getClientId().then(setClientId)
    }, [])

    const onSuccess = () => {
        console.log("Logout success")
        setImageUrl('')
    }


    return (
        <div>
            {clientId &&
            <GoogleLogout
                clientId={clientId}
                buttonText="Logout"
                onSuccess={onSuccess}
            />}
        </div>
    )
}