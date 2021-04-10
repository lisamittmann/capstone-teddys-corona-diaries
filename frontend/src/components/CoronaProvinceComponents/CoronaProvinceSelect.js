import styled from 'styled-components/macro'

export default function CoronaProvinceSelect({provinces, setSelectedProvince}) {

    return (
        <SelectWrapper>
            <p>Wähle Deine Region aus:</p>
            <ProvinceSelector required defaultValue={''} onChange={e => setSelectedProvince(e.target.value)}>
                <option value="" disabled hidden>
                    Deine Region
                </option>
                {provinces
                    .slice()
                    .sort((oneProvince, anotherProvince) => oneProvince.localeCompare(anotherProvince))
                    .map(province => (
                    <option key={province} value={province}>{province}</option>
                ))}
            </ProvinceSelector>
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
