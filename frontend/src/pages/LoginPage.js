import OverviewPageLayout from "../components/PageLayoutComponents/OverviewPageLayout";
import PageHeader from "../components/PageLayoutComponents/PageHeader";
import LoginWithGoogle from "../components/LoginComponents/LoginWithGoogle";
import styled from 'styled-components/macro'
import PageLayoutWithNavigation from "../components/NavigationComponents/PageLayoutWithNavigation";

export default function LoginPage() {

    return (
        <PageLayoutWithNavigation>
            <OverviewPageLayout>
                <PageHeader>Login</PageHeader>
                <section>
                    <Logo src="https://storage.googleapis.com/teddys-corona-diaries-images/teddy-frontcover.jpg"
                          alt="Logo"/>
                    <LoginButtonWrapper>
                        <p>Willkommen</p>
                        <p>Bitte logge Dich ein</p>
                        <LoginWithGoogle/>
                    </LoginButtonWrapper>
                </section>
            </OverviewPageLayout>
        </PageLayoutWithNavigation>
    )


}


const LoginButtonWrapper = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 25px;
  font-family: Roboto;

  p:nth-child(1) {
    font-weight: bold;
  }

  p:nth-child(2) {
    font-size: 14px;
    margin-bottom: 25px;
  }
`

const Logo = styled.img`
  width: 150px;
  border-radius: 50%;
  height: 150px;
  object-fit: cover;
  display: block;
  margin-top: 25px;
  margin-left: auto;
  margin-right: auto;
`