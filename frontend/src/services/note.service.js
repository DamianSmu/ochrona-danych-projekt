import authHeader from "./auth-header";
import axios from "axios";

const API_URL = "https://localhost:8080/api/note/";

const getAll = (isPublic) => {
    var URL = API_URL;
    if(isPublic){
        URL = URL + "public/"
    }
    return axios.get(URL , { headers: authHeader(), withCredentials: true });
};

const add = (isPublic, body) => {
    var URL = API_URL;
    if(isPublic){
        URL = URL + "public/"
    }
    return axios.post(URL, {body}, {headers: authHeader(), withCredentials: true });
}

const deleteNote = (isPublic, id) => {
    var URL = API_URL;
    if(isPublic){
        URL = URL + "public/"
    }
    return axios.delete(URL + id,{headers: authHeader(), withCredentials: true });
}

export default {
    getAll,
    add,
    deleteNote
};
