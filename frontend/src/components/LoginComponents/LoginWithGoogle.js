import {getClientId, loginUserWithGoogle} from "../../service/googleAuthService";
import {useEffect, useState} from "react";
import GoogleLogin from "react-google-login";
import {useAuth} from "./AuthContext";

export default function LoginWithGoogle() {

    const { token, setToken } = useAuth()
    const [clientId, setClientId] = useState()

    useEffect(() => {
        getClientId().then(setClientId)
    }, [])

    const onSuccess = (res) => {
        const loginDto = {profileObj: res.profileObj, tokenId: res.tokenId}
        loginUserWithGoogle(loginDto).then(setToken)
        console.log("Token: ", token)
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