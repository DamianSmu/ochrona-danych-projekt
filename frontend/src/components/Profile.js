import React, {useEffect, useState} from "react";
import AuthService from "../services/auth.service";
import ChangePassword from "./ChangePassword";
import {useHistory} from "react-router-dom";

const Profile = () => {
    const [id, setId] = useState("");
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [roles, setRoles] = useState([]);
    const [ip, setIp] = useState([]);

    const history = useHistory();
    useEffect(() => {
        const user = AuthService.getCurrentUser();
        if (user) {
            setId(user.id);
            setEmail(user.email);
            setRoles(user.roles);
            setUsername(user.username);
            setIp(user.ip);
        }
    }, []);

    return (
        <div className="container">
            <header className="jumbotron">
                <h3>
                    <strong>{username}</strong> Profile
                </h3>
            </header>
            <p>
                <strong>Id:</strong> {id}
            </p>
            <p>
                <strong>Email:</strong> {email}
            </p>
            <strong>Authorities:</strong>
            <ul>
                {roles &&
                roles.map((role, index) => <li key={index}>{role}</li>)}
            </ul>
            <strong>Ip addresses:</strong>
            <ul>
                {ip &&
                ip.map((role, index) => <li key={index}>{role}</li>)}
            </ul>

            <div className="col-sm-2">
                <button className="btn btn-primary btn-block" onClick={() => {
                    AuthService.logoutAll().then(() => {
                        history.push("/login");
                        window.location.reload();
                    });
                }}>Logout everywhere
                </button>
            </div>
            <ChangePassword></ChangePassword>
        </div>
    );
};

export default Profile;
