import styled from 'styled-components/macro'
import {useAuth} from "../LoginComponents/AuthContext";
import {AiFillHeart, AiOutlineHeart} from 'react-icons/ai';
import {useEffect, useState} from "react";
import {deleteRecipe, getRecipeStatus, postSaveRecipe} from "../../service/userApiService";

export default function RecipeHeader({recipeName, recipeDescription, recipeId}) {

    const {token} = useAuth()

    const [recipeStatus, setRecipeStatus] = useState()

    const saveRecipe = () => {

        const updatedStatus = recipeStatus === "saved" ? "not-saved" : "saved"
        if(recipeStatus === "saved") {
            deleteRecipe(recipeId).then(() => setRecipeStatus(updatedStatus))
        } else {
            postSaveRecipe(recipeId).then(() => setRecipeStatus(updatedStatus))
        }

    }

    useEffect(() => {
        getRecipeStatus(recipeId).then(setRecipeStatus)
    }, [recipeId])

    return (
        <Header>
            <Wrapper>
                <h3>{recipeName}</h3>
                {token &&
                    <ButtonBubble onClick={saveRecipe}>
                        {recipeStatus === "saved" ?  <AiFillHeart/> : <AiOutlineHeart/>}
                    </ButtonBubble>
                }
            </Wrapper>
            <p>{recipeDescription}</p>
        </Header>

    )
}

const Wrapper = styled.section`
  display: grid;
  grid-template-columns: 1fr auto;
  align-items: center;

  h3 {
    padding: 10px 25px;
    margin: 0;
    font-size: 22px;
  }
`

const ButtonBubble = styled.span`
  background-color: var(--color-turquoise-green);
  width: 50px;
  height: 50px;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 12px 12px;
`

const Header = styled.section`
  display: grid;
  grid-auto-rows: auto auto;

  p {
    padding: 10px 25px 10px 25px;
    font-size: var(--standard-font-size);
    color: var(--color-battleship-grey);
    margin: 0;
  }
`