import OverviewPageLayout from "../components/PageLayoutComponents/OverviewPageLayout";
import PageHeader from "../components/PageLayoutComponents/PageHeader";
import LogoutWithGoogle from "../components/LoginComponents/LogoutWithGoogle";
import PageLayoutWithNavigation from "../components/NavigationComponents/PageLayoutWithNavigation";
import styled from 'styled-components/macro'
import {useAuth} from "../components/LoginComponents/AuthContext";
import RecipeCard from "../components/RecipeCardComponents/RecipeCard";
import {useEffect, useState} from "react";
import {getSavedRecipes} from "../service/userApiService";

export default function UserAccountPage() {

    const {userInfo} = useAuth()
    const [savedRecipes, setSavedRecipes] = useState()

    useEffect(() => {
        getSavedRecipes().then(setSavedRecipes)
    }, [])

    return (
        <PageLayoutWithNavigation>
            <OverviewPageLayout>
                <PageHeader>Mein Bereich</PageHeader>
                <UserContent>
                    <img src={userInfo.avatarUrl} alt={userInfo.name}/>
                    <p>Hallo {userInfo.name}</p>
                    <LogoutWithGoogle/>
                    <RecipeList>
                        {savedRecipes &&
                        savedRecipes
                            .slice()
                            .sort((oneRecipe, anotherRecipe) => oneRecipe.quarantineDay - anotherRecipe.quarantineDay)
                            .map(recipe => <RecipeCard recipeCardDetails={recipe} key={recipe.quarantineDay} from={"me"}/>)
                        }
                    </RecipeList>
                </UserContent>
            </OverviewPageLayout>
        </PageLayoutWithNavigation>
    )
}

const UserContent = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 25px;
  overflow-y: scroll;
  margin-bottom: 25px;

  img {
    border-radius: 50%;
  }
  
  p {
    font-family: Roboto;
    font-size: 14px;
  }
`

const RecipeList = styled.section`
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 350px));
  grid-gap: 20px;
  font-family: Roboto;
  margin: 40px 20px 0px 20px;
`