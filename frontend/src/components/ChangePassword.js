import React, { useState, useRef } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import {calcEntropy, required, vpassword} from "./Validations"
import AuthService from "../services/auth.service";
import { useHistory } from "react-router-dom";

const ChangePassword = (props) => {
    const form = useRef();
    const checkBtn = useRef();
    const history = useHistory();

    const [oldPassword, setoldPassword] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState("");
    const [entropyMessage, setEntropyMessage] = useState("");

    const onChangeoldPassword = (e) => {
        const oldPassword = e.target.value;
        setoldPassword(oldPassword);
    };



    const onChangePassword = (e) => {
        form.current.validateAll();
        const password = e.target.value;
        setEntropyMessage(() => {
            var entropy = calcEntropy(password)
            if(entropy >= 70) {
                return (
                    <div className="alert alert-success" role="alert">
                        Password is strong.
                        <br/>
                        Entropy: {entropy.toFixed(0)} bits.
                    </div>
                );
            }
        })
        setPassword(password);
    };

    const handleLogin = (e) => {
        e.preventDefault();

        setMessage("");
        setLoading(true);

        form.current.validateAll();

        if (checkBtn.current.context._errors.length === 0) {
            AuthService.changePassword(oldPassword, password).then(
                (response) => {if(response.status === 200){
                    AuthService.logout();
                    history.push("/login");
                    window.location.reload();
                }
                },
                (error) => {
                    const resMessage =
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.message ||
                        error.toString();

                    setLoading(false);
                    setMessage(resMessage);
                }
            );
        } else {
            setLoading(false);
        }
    };

    return (
        <div className="col-md-12">
            <div className="card card-container">
                <h5>Change password</h5>
                <Form onSubmit={handleLogin} ref={form}>
                    <div className="form-group">
                        <label htmlFor="oldPassword">Old password</label>
                        <Input
                            type="password"
                            className="form-control"
                            name="oldPassword"
                            value={oldPassword}
                            onChange={onChangeoldPassword}
                            validations={[required]}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="password">New password</label>
                        <Input
                            type="password"
                            className="form-control"
                            name="password"
                            value={password}
                            onChange={onChangePassword}
                            validations={[required, vpassword]}
                        />
                        {entropyMessage}
                    </div>

                    <div className="form-group">
                        <button className="btn btn-primary btn-block" disabled={loading}>
                            {loading && (
                                <span className="spinner-border spinner-border-sm"/>
                            )}
                            <span>Change</span>
                        </button>
                    </div>

                    {message && (
                        <div className="form-group">
                            <div className="alert alert-danger" role="alert">
                                {message}
                            </div>
                        </div>
                    )}
                    <CheckButton style={{ display: "none" }} ref={checkBtn} />
                </Form>
            </div>
        </div>
    );
};

export default ChangePassword;
