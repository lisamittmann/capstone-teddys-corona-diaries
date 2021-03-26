import styled from 'styled-components/macro'
import {useState} from "react";

export default function CoronaProvinceSelect() {
    const provinces = [
        {label: 'Hamburg', value: 'Hamburg'},
        {label: 'Berlin', value: 'Berlin'},
        {label: 'Bayern', value: 'Bayern'},
    ]

    const [selectedProvince, setSelectedProvince] = useState()

    const selectProvince = (e) => setSelectedProvince(e.target.value)


    return (

        <SelectWrapper>
            <p>Wähle Deine Region aus:</p>
            <ProvinceSelector required defaultValue={''} onChange={e => selectProvince(e)}>
                <option value="" disabled hidden>
                    Deine Region
                </option>
                {provinces.map(province => (
                    <option key={province.label} value={province.value}>{province.label}</option>
                ))}
            </ProvinceSelector>
            {selectedProvince && <p>{selectedProvince}</p>}
        </SelectWrapper>
    )
}

const ProvinceSelector = styled.select`
  width: 100%;
  appearance: none;
  background: var(--color-cultured);
  border: none;
  box-shadow: 2.5px 2.5px 2.5px var(--color-silver);
  cursor: pointer;
  padding: 12px 12px 12px 12px;
  font-size: 14px;
  border-radius: 5px;
`

const SelectWrapper = styled.div`
    p {
      font-size: var(--standard-font-size);
    }
`
