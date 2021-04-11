import OverviewPageLayout from "../components/PageLayoutComponents/OverviewPageLayout";
import PageHeader from "../components/PageLayoutComponents/PageHeader";
import LogoutWithGoogle from "../components/LoginComponents/LogoutWithGoogle";
import PageLayoutWithNavigation from "../components/NavigationComponents/PageLayoutWithNavigation";
import styled from 'styled-components/macro'
import {useAuth} from "../components/LoginComponents/AuthContext";

export default function UserAccountPage() {

    const {userInfo} = useAuth()

    return (
        <PageLayoutWithNavigation>
            <OverviewPageLayout>
                <PageHeader>Mein Bereich</PageHeader>
                <UserContent>
                    <img src={userInfo.avatarUrl} alt={userInfo.name}/>
                    <p>Hallo {userInfo.name}</p>
                    <LogoutWithGoogle/>
                </UserContent>
            </OverviewPageLayout>
        </PageLayoutWithNavigation>
    )
}

const UserContent = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 25px;

  img {
    border-radius: 50%;
  }
  
  p {
    font-family: Roboto;
    font-size: 14px;
  }
`