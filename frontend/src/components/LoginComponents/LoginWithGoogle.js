import {getClientId, loginUserWithGoogle} from "../../service/googleAuthService";
import {useEffect, useState} from "react";
import GoogleLogin from "react-google-login";
import {useAuth} from "./AuthContext";
import {useHistory} from "react-router";

export default function LoginWithGoogle() {

    const { setToken } = useAuth()
    const [clientId, setClientId] = useState()
    const history = useHistory()

    useEffect(() => {
        getClientId().then(setClientId)
    }, [])

    const onSuccess = (res) => {
        const loginDto = {profileObj: res.profileObj, tokenId: res.tokenId}
        loginUserWithGoogle(loginDto).then(setToken).then(() => history.push("/me"))
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