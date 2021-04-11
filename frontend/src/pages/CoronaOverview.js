import PageHeader from "../components/PageLayoutComponents/PageHeader";
import OverviewPageLayout from "../components/PageLayoutComponents/OverviewPageLayout";
import CoronaDetailsForProvince from "../components/CoronaProvinceComponents/CoronaDetailsForProvince";
import PageLayoutWithNavigation from "../components/NavigationComponents/PageLayoutWithNavigation";

export default function CoronaOverview() {
    return (
        <PageLayoutWithNavigation>
            <OverviewPageLayout>
                <PageHeader>Corona Lage</PageHeader>
                <CoronaDetailsForProvince/>
            </OverviewPageLayout>
        </PageLayoutWithNavigation>
    )
}