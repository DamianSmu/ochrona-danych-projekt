import axios from "axios";
import authHeader from "./auth-header";
axios.defaults.withCredentials = true;

const API_URL = "/api/auth/";

const register = (username, email, password) => {
    return axios.post(API_URL + "signup", {
        username,
        email,
        password,
    });
};

const login = (username, password, ip) => {
    console.log("login");
    return axios
        .post(API_URL + "signin", {
            username,
            password,
            ip
        }, { withCredentials: true })
        .then((response) => {
            console.log(response);
            if (response.data.accessToken) {
                localStorage.setItem("user", JSON.stringify(response.data));
            }
            return response.data;
        });
};

const resetPassword = (token, password) => {
    return axios
        .post(API_URL + "resetPassword", {
            token,
            password,
        })
        .then((response) => {
            return response.data;
        });
};

const resetPasswordRequest = (email) => {
    return axios
        .post(API_URL + "getResetPasswordToken", {
            email,
        });
};

const logout = () => {
    localStorage.removeItem("user");
};

const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem("user"));

};

const changePassword = (oldPassword, newPassword) => {
    return axios
        .post("/api/user/changePassword", {
            oldPassword,
            newPassword,
        }, {headers: authHeader(), withCredentials: true })
        .then((response) => {
            return response;
        });
};

const logoutAll = () => {
    return axios
        .get("/api/user/logoutAll", {headers: authHeader(), withCredentials: true })
        .then((response) => {
            logout();
            return response;
        });
};


export default {
    register,
    login,
    logout,
    getCurrentUser,
    resetPassword,
    resetPasswordRequest,
    changePassword,
    logoutAll
};
