import {getClientId} from "../../service/googleAuthService";
import {useEffect, useState} from "react";
import GoogleLogin from "react-google-login";

export default function LoginWithGoogle({setImageUrl}) {

    const [clientId, setClientId] = useState()

    useEffect(() => {
        getClientId().then(setClientId)
    }, [])

    const onSuccess = (res) => {
        console.log("Login success, current user: ", res)
        setImageUrl(res.profileObj.imageUrl)
    }

    const onFailure = (res) => {
        console.log("Login failed, res: ", res)
    }

    return(
        <div>
            {clientId &&
            <GoogleLogin
                clientId={clientId}
                buttonText="Login"
                onSuccess={onSuccess}
                onFailure={onFailure}
                cookiePolicy={'single_host_origin'}
                isSignedIn={true}
            />}
        </div>
    )
}