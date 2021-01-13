import axios from "axios";
axios.defaults.withCredentials = true;

const API_URL = "https://localhost:8080/api/auth/";

const register = (username, email, password) => {
    return axios.post(API_URL + "signup", {
        username,
        email,
        password,
    });
};

const login = (username, password) => {
    return axios
        .post(API_URL + "signin", {
            username,
            password,
        }, { withCredentials: true })
        .then((response) => {
            if (response.data.accessToken) {
                console.log(response.data.accessToken)
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
    var user = JSON.parse(localStorage.getItem("user"));
    if(user && (user.expDate > Date.now())) {
        return user;
    } else {
        localStorage.removeItem("user");
    }
    return null;
};


export default {
    register,
    login,
    logout,
    getCurrentUser,
    resetPassword,
    resetPasswordRequest
};
