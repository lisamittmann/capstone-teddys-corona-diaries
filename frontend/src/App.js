import './App.css';
import {Link, Route, Switch} from "react-router-dom";
import RecipePage from "./pages/RecipePage";
import RecipeOverview from "./pages/RecipeOverview";
import NavigationBar from "./components/NavigationComponents/NavigationBar";
import PageLayoutWithNavigation from "./components/NavigationComponents/PageLayoutWithNavigation";
import CoronaOverview from "./pages/CoronaOverview";
import Homepage from "./pages/Homepage";

function App() {
    return (
        <div>
            <Switch>
                <Route exact path="/">
                    <Homepage/>
                </Route>
                <Route path="/recipe/:recipeId">
                    <RecipePage/>
                </Route>
                <PageLayoutWithNavigation>
                    <Route exact path="/">
                        <Link to="/recipes">Rezept-Übersicht</Link>
                    </Route>
                    <Route path="/recipes">
                        <RecipeOverview/>
                    </Route>
                    <Route path="/coronadetails">
                        <CoronaOverview/>
                    </Route>
                    <NavigationBar/>
                </PageLayoutWithNavigation>
            </Switch>
        </div>
    )
        ;
}

export default App;
