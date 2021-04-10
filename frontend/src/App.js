import './App.css';
import {Route, Switch} from "react-router-dom";
import RecipePage from "./pages/RecipePage";
import RecipeOverview from "./pages/RecipeOverview";
import NavigationBar from "./components/NavigationComponents/NavigationBar";
import PageLayoutWithNavigation from "./components/NavigationComponents/PageLayoutWithNavigation";
import CoronaOverview from "./pages/CoronaOverview";
import Homepage from "./pages/Homepage";
import CoronaActivitiesPage from "./pages/CoronaActivitiesPage";
import LoginPage from "./pages/LoginPage";
import AuthProvider from "./components/LoginComponents/AuthProvider";
import ProtectedRoute from "./components/LoginComponents/ProtectedRoute";
import UserAccountPage from "./pages/UserAccountPage";

function App() {
    return (
        <AuthProvider>
            <Switch>
                <Route exact path="/" component={Homepage}/>
                <Route path="/recipe/:recipeId">
                    <RecipePage/>
                </Route>
                <PageLayoutWithNavigation>
                    <Route path="/recipes" component={RecipeOverview}/>
                    <Route path="/coronadetails" component={CoronaOverview}/>
                    <Route path="/activities" component={CoronaActivitiesPage}/>
                    <Route path="/login" component={LoginPage}/>
                    <ProtectedRoute path="/me" component={UserAccountPage}/>
                    <NavigationBar/>
                </PageLayoutWithNavigation>
            </Switch>
        </AuthProvider>
    )
        ;
}

export default App;
