import './App.css';
import {Route} from "react-router-dom";
import RecipePage from "./pages/RecipePage";

function App() {
    return (
        <Route path="/recipe/:recipeId">
            <RecipePage/>
        </Route>
    );
}

export default App;
