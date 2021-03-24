import styled from 'styled-components/macro'

export default function PageHeader({headerText}) {
    return (
        <HeaderWrapper>
            {headerText}
        </HeaderWrapper>
    )
}

const HeaderWrapper = styled.section`
  background: var(--color-turquoise-green);
  font-family: Halant;
  font-size: 30px;
  padding: 20px 20px 20px 45px;
  color: var(--color-cultured)
`