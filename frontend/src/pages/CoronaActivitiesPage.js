import OverviewPageLayout from "../components/PageLayoutComponents/OverviewPageLayout";
import PageHeader from "../components/PageLayoutComponents/PageHeader";
import CoronaActivitiesDetails from "../components/CoronaActivitiesComponents/CoronaActivitiesDetails";
import PageLayoutWithNavigation from "../components/NavigationComponents/PageLayoutWithNavigation";

export default function CoronaActivitiesPage() {

    return (
        <PageLayoutWithNavigation>
            <OverviewPageLayout>
                <PageHeader>Aktivitäten</PageHeader>
                <CoronaActivitiesDetails/>
            </OverviewPageLayout>
        </PageLayoutWithNavigation>
    )

}