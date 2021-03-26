import PageHeader from "../components/PageLayoutComponents/PageHeader";
import OverviewPageLayout from "../components/PageLayoutComponents/OverviewPageLayout";
import CoronaDetailsForProvince from "../components/CoronaProvinceComponents/CoronaDetailsForProvince";

export default function CoronaOverview() {
    return (
        <OverviewPageLayout>
            <PageHeader>Corona Lage</PageHeader>
            <CoronaDetailsForProvince/>
        </OverviewPageLayout>
    )
}