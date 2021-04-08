import OverviewPageLayout from "../components/PageLayoutComponents/OverviewPageLayout";
import PageHeader from "../components/PageLayoutComponents/PageHeader";
import LoginWithGoogle from "../components/LoginComponents/LoginWithGoogle";
import LogoutWithGoogle from "../components/LoginComponents/LogoutWithGoogle";
import styled from 'styled-components/macro'
import {useState} from "react";
import {useAuth} from "../components/LoginComponents/AuthContext";

export default function LoginPage() {

    const { token, setToken } = useAuth()


    return (
        <OverviewPageLayout>
            <PageHeader>Mein Bereich</PageHeader>
            <section>
                <LoginButtonWrapper>
                    <LoginWithGoogle setImageUrl={setImageUrl}/>
                    <LogoutWithGoogle setImageUrl={setImageUrl}/>
                </LoginButtonWrapper>
            </section>
        </OverviewPageLayout>
    )


}



const LoginButtonWrapper = styled.section`
  display: flex;
  flex-flow: nowrap;
  justify-content: space-evenly;
`