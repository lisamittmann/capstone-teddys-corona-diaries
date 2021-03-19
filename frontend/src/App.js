import './App.css';
import {Link, Route} from "react-router-dom";
import RecipePage from "./pages/RecipePage";

function App() {
    return (
        <div>
            <Route exact path="/">
                <p>This is my starter page</p>
                <Link to="/recipe/day33">Schokokuchen</Link>
            </Route>
            <Route path="/recipe/:recipeId">
                <RecipePage/>
            </Route>
        </div>
    );
}

export default App;
