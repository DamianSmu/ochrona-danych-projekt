import React, { useState, useRef } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import AuthService from "../services/auth.service";
import {calcEntropy, required, vpass, vpassword} from "./Validations";

const ResetPassword = (props) => {
    const form = useRef();
    const checkBtn = useRef();

    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState("");
    const [entropyMessage, setEntropyMessage] = useState("");

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

        let search = window.location.search;
        let params = new URLSearchParams(search);
        let token = params.get('token');

        if(!token){
            const resMessage = "Invalid token."
            setLoading(false);
            setMessage(resMessage);
        }

        if (checkBtn.current.context._errors.length === 0) {
            AuthService.resetPassword(token, password).then(
                () => {
                    AuthService.logout();
                    props.history.push("/login");
                    window.location.reload();
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
                <h5>Reset password</h5>
                <Form onSubmit={handleLogin} ref={form}>

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
                            <span>Reset</span>
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

export default ResetPassword;
