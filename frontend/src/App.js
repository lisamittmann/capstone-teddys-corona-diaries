import './App.css';
import {Route, Switch} from "react-router-dom";
import RecipePage from "./pages/RecipePage";
import RecipeOverview from "./pages/RecipeOverview";
import CoronaOverview from "./pages/CoronaOverview";
import Homepage from "./pages/Homepage";
import CoronaActivitiesPage from "./pages/CoronaActivitiesPage";
import LoginPage from "./pages/LoginPage";
import ProtectedRoute from "./components/LoginComponents/ProtectedRoute";
import UserAccountPage from "./pages/UserAccountPage";

function App() {
    return (
        <Switch>
            <Route exact path="/" component={Homepage}/>
            <Route exact path="/recipe/:recipeId">
                <RecipePage/>
            </Route>

            <Route exact path="/recipes" component={RecipeOverview}/>
            <Route exact path="/coronadetails" component={CoronaOverview}/>
            <Route exact path="/activities" component={CoronaActivitiesPage}/>
            <Route exact path="/login" component={LoginPage}/>

            <ProtectedRoute exact path="/me">
                <UserAccountPage/>
            </ProtectedRoute>
        </Switch>
    )
        ;
}

export default App;
