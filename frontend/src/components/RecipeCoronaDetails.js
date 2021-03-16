import styled from 'styled-components/macro'

export default function RecipeCoronaDetails() {
    return (
        <CoronaBox>
            <div>Corona Details here</div>
        </CoronaBox>
    )
}

const CoronaBox = styled.section`
  display: flex;
  justify-content: center;
  height: 45px;
  font-size: 16px;
  
  div {
    background: var(--color-alabaster);
    margin-left: 25px;
    margin-right: 25px;
    width: 100%;
    padding: 10px 16px 10px 16px;
  }
`