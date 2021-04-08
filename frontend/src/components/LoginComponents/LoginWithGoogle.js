import {getClientId, loginUserWithGoogle} from "../../service/googleAuthService";
import {useEffect, useState} from "react";
import GoogleLogin from "react-google-login";
import {useAuth} from "./AuthContext";

export default function LoginWithGoogle({setImageUrl}) {

    const { token, setToken } = useAuth()
    const [clientId, setClientId] = useState()

    useEffect(() => {
        getClientId().then(setClientId)
    }, [])

    const onSuccess = (res) => {
        console.log("Login success, current user: ", res)
        const loginDto = {profileObj: res.profileObj, tokenId: res.tokenId}
        loginUserWithGoogle(loginDto).then(setToken)
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