import styled from 'styled-components/macro'
import NavigationBar from "./NavigationBar";

export default function PageLayoutWithNavigation({children}) {
    return(
        <Layout>
            {children}
            <NavigationBar/>
        </Layout>
    )
}


const Layout = styled.section`
  display: grid;
  height: 100vh;
  grid-template-rows: 1fr auto;
`