import './App.css';
import {Link, Route, Switch} from "react-router-dom";
import RecipePage from "./pages/RecipePage";
import RecipeOverview from "./pages/RecipeOverview";
import NavigationWrapper from "./components/NavigationComponents/NavigationWrapper";
import NavigationBar from "./components/NavigationComponents/NavigationBar";

function App() {
    return (
        <div>
            <Switch>
                <Route path="/recipe/:recipeId">
                    <RecipePage/>
                </Route>
                <NavigationWrapper>
                    <Route exact path="/">
                        <Link to="/recipes">Rezept-Übersicht</Link>
                    </Route>
                    <Route path="/recipes">
                        <RecipeOverview/>
                    </Route>
                    <NavigationBar/>
                </NavigationWrapper>
            </Switch>
        </div>
    )
        ;
}

export default App;
