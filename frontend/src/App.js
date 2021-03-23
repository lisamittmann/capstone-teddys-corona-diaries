import './App.css';
import {Link, Route, Switch} from "react-router-dom";
import RecipePage from "./pages/RecipePage";
import RecipeOverview from "./pages/RecipeOverview";

function App() {
    return (
        <div>
            <Switch>
                <Route exact path="/">
                    <p>This is my starter page</p>
                    <Link to="/recipe/day33">Schokokuchen</Link>
                </Route>
                <Route path="/recipes">
                    <RecipeOverview/>
                </Route>
                <Route path="/recipe/:recipeId">
                    <RecipePage/>
                </Route>
            </Switch>
        </div>
    );
}

export default App;
