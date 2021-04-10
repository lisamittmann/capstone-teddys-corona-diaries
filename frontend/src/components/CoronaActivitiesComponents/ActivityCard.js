import styled from 'styled-components/macro'

export default function ActivityCard({activity}) {
    return (
        <ActivityWrapper>
            <img src={activity.imageUrl} alt={activity.name}/>
            <Description>
                <p>{activity.name}</p>
                <p>{activity.description}</p>
            </Description>
        </ActivityWrapper>
    )
}

const ActivityWrapper = styled.section`
  display: grid;
  grid-template-columns: auto 1fr;
  margin: 5px 25px;
  grid-gap: 16px;
  background-color: var(--color-cultured);
  box-shadow: 2px 2px 2px var(--color-silver);
  
  img {
    width: 150px;
    height: auto;
    display: block;
    object-fit: cover;
  }
  
  p {
    padding-right: 12px;
  }
  
  p:nth-child(1) {
    font-size: var(--standard-font-size);
    font-weight: bold;
  }
  
  p:nth-child(2) {
    font-size: 14px;
  }
`

const Description = styled.div`

`