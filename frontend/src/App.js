import React, {useEffect, useState} from "react";
import {Link, Route, Switch} from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AuthService from "./services/auth.service";

import Login from "./components/Login";
import Register from "./components/Register";
import Profile from "./components/Profile";
import ResetPassword from "./components/ResetPassword";
import RequestForResetPassword from "./components/RequestForResetPassword";
import PublicNotes from "./components/PublicNotes";
import PrivateNotes from "./components/PrivateNotes";


const App = () => {
    const [currentUser, setCurrentUser] = useState(undefined);

    useEffect(() => {
        const user = AuthService.getCurrentUser();

        if (user) {
            setCurrentUser(user);
        }
    }, []);

    const logOut = () => {
        AuthService.logout();
    };

    return (
        <div>
            <nav className="navbar navbar-expand navbar-dark bg-dark">
                <Link to={"/"} className="navbar-brand">
                    Ochrona danych - Projekt - Damian Smugorzewski
                </Link>

                    {currentUser && (<div className="navbar-nav mr-auto">
                            <li className="nav-item">
                                <Link to={"/publicNotes"} className="nav-link">
                                    Public notes
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link to={"/privateNotes"} className="nav-link">
                                    Private notes
                                </Link>
                            </li>
                    </div>)}

                {currentUser ? (
                    <div className="navbar-nav ml-auto">
                        <li className="nav-item">
                            <Link to={"/profile"} className="nav-link">
                                Profile
                            </Link>
                        </li>
                        <li className="nav-item">
                            <a href={"/login"} className="nav-link" onClick={logOut}>
                                Logout
                            </a>
                        </li>
                    </div>
                ) : (
                    <div className="navbar-nav ml-auto">
                        <li className="nav-item">
                            <Link to={"/login"} className="nav-link">
                                Login
                            </Link>
                        </li>

                        <li className="nav-item">
                            <Link to={"/register"} className="nav-link">
                                Sign Up
                            </Link>
                        </li>
                    </div>
                )}
            </nav>

            <div className="container mt-3">
                <Switch>
                    <Route exact path={["/", "/login"]} component={Login}/>
                    <Route exact path="/register" component={Register}/>
                    <Route exact path="/profile" component={Profile}/>
                    <Route exact path="/resetPassword" component={ResetPassword}/>
                    <Route exact path="/requestForResetPassword" component={RequestForResetPassword}/>
                    <Route path="/publicNotes" component={PublicNotes}/> />
                    <Route path="/privateNotes" component={PrivateNotes}/>
                </Switch>
            </div>
        </div>
    );
};

export default App;
