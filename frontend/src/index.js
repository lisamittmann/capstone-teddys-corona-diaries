import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import GlobalStyle from "./components/GlobalStyle";
import {BrowserRouter as Router} from 'react-router-dom'
import AuthProvider from "./components/LoginComponents/AuthProvider";

ReactDOM.render(
    <React.StrictMode>

        <Router>
            <AuthProvider>
                <GlobalStyle/>
                <App/>
            </AuthProvider>
        </Router>

    </React.StrictMode>,
    document.getElementById('root')
);

