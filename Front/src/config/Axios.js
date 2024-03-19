import axios from "axios";

const SERVER_URL = process.env.VUE_APP_BASE_URL;

const instance = axios.create({
    baseURL: SERVER_URL,
    timeout: 3000
});

export default instance;
