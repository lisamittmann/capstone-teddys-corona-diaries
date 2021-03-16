import logo from './logo.svg';
import './App.css';
import {Route} from "react-router-dom";
import RecipePage from "./pages/RecipePage";

function App() {
    return (
        <div>
            <Route path="/recipe/:recipeId">
                <RecipePage/>
            </Route>
        </div>
    );
}

export default App;
