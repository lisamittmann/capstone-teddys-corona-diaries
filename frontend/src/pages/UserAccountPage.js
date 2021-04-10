import OverviewPageLayout from "../components/PageLayoutComponents/OverviewPageLayout";
import PageHeader from "../components/PageLayoutComponents/PageHeader";
import LogoutWithGoogle from "../components/LoginComponents/LogoutWithGoogle";

export default function UserAccountPage() {
    return (
        <OverviewPageLayout>
            <PageHeader>Mein Bereich</PageHeader>
            <LogoutWithGoogle/>
        </OverviewPageLayout>
    )
}