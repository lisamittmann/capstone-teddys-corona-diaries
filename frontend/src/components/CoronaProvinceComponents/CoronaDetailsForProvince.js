import CoronaProvinceSelect from "./CoronaProvinceSelect";
import styled from 'styled-components/macro'
import {useEffect, useState} from "react";
import {getProvinces} from "../../service/covidApiService";
import CoronaProvinceCard from "./CoronaProvinceCard";

export default function CoronaDetailsForProvince() {

    const [provinces, setProvinces] = useState()
    const [selectedProvince, setSelectedProvince] = useState()

    useEffect(() => {
        getProvinces().then(setProvinces)
    }, [])

    return (
        <ProvinceWrapper>
            {provinces && <CoronaProvinceSelect provinces={provinces} setSelectedProvince={setSelectedProvince}/>}
            {selectedProvince &&
            <CoronaProvinceCard province={selectedProvince}/>}
        </ProvinceWrapper>
    )
}

const ProvinceWrapper = styled.section`
  padding-right: var(--medium-padding);
  padding-left: var(--medium-padding);
  font-family: Roboto;
  margin-top: 25px;
  display: grid;
  grid-template-rows: auto 1fr;
  grid-gap: 25px;
`