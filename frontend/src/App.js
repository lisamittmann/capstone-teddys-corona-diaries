import './App.css';
import {Link, Route, Switch} from "react-router-dom";
import RecipePage from "./pages/RecipePage";
import RecipeOverview from "./pages/RecipeOverview";
import NavigationBar from "./components/NavigationComponents/NavigationBar";
import PageLayoutWithNavigation from "./components/NavigationComponents/PageLayoutWithNavigation";
import CoronaOverview from "./pages/CoronaOverview";
import Homepage from "./pages/Homepage";
import CoronaActivitiesPage from "./pages/CoronaActivitiesPage";

function App() {
    return (
        <Switch>
            <Route exact path="/" component={Homepage}/>
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
                <Route path="/activities">
                    <CoronaActivitiesPage/>
                </Route>
                <NavigationBar/>
            </PageLayoutWithNavigation>
        </Switch>
    )
        ;
}

export default App;
