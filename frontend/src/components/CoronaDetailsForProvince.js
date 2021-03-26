import CoronaProvinceSelect from "./CoronaProvinceSelect";
import styled from 'styled-components/macro'

export default function CoronaDetailsForProvince(){
    return(
        <ProvinceWrapper>
            <CoronaProvinceSelect/>
        </ProvinceWrapper>
    )
}

const ProvinceWrapper = styled.section`
  padding-right: var(--medium-padding);
  padding-left: var(--medium-padding);
  font-family: Roboto;
  margin-top: 25px;
`