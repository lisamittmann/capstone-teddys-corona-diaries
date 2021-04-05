import OverviewPageLayout from "../components/PageLayoutComponents/OverviewPageLayout";
import PageHeader from "../components/PageLayoutComponents/PageHeader";
import CoronaActivitiesDetails from "../components/CoronaActivitiesComponents/CoronaActivitiesDetails";

export default function CoronaActivitiesPage() {

    return(
        <OverviewPageLayout>
            <PageHeader>Aktivitäten</PageHeader>
            <CoronaActivitiesDetails/>
        </OverviewPageLayout>
    )

}