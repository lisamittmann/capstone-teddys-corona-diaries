import OverviewPageLayout from "../components/PageLayoutComponents/OverviewPageLayout";
import PageHeader from "../components/PageLayoutComponents/PageHeader";
import LogoutWithGoogle from "../components/LoginComponents/LogoutWithGoogle";
import PageLayoutWithNavigation from "../components/NavigationComponents/PageLayoutWithNavigation";

export default function UserAccountPage() {
    return (
        <PageLayoutWithNavigation>
            <OverviewPageLayout>
                <PageHeader>Mein Bereich</PageHeader>
                <LogoutWithGoogle/>
            </OverviewPageLayout>
        </PageLayoutWithNavigation>
    )
}