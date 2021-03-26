import styled from 'styled-components/macro'
import {ThemeProvider} from "styled-components";

export default function CoronaProvinceCard({province}) {

    let theme = {main: "var(--color-silver)"}


    return (
        <CoronaCard>
            <ThemeProvider theme={theme}>
                <Rectangle/>
            </ThemeProvider>
            <p>{province}</p>
        </CoronaCard>
    )
}

const CoronaCard = styled.section`
  display: grid;
  grid-template-columns: auto 1fr;
  align-items: center;
  font-size: var(--standard-font-size);
  background: var(--color-cultured);
  margin-top: 25px;
  box-shadow: 2.5px 2.5px 2.5px var(--color-silver);
  border-radius: 5px;
  grid-gap: 10px;
`

const Rectangle = styled.span`
  display: inline-block;
  background: #94CBB1;
  width: 15px;
  height: 70%;
  margin-left: 16px;
  box-shadow: 1px 1px 1px var(--color-silver);
  background: ${props => props.theme.main}
`