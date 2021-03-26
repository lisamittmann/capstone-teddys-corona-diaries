import styled from 'styled-components/macro'

export default styled.div`
  display: grid;
  grid-template-rows: auto 1fr;
  height: calc(100vh - var(--navigation-bar-height));
`