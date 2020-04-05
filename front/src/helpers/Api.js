import axios from 'axios';

export const AUTH_HEADER_TOKEN = 'authenticationToken';

export default axios.create({
    baseURL: 'http://localhost:8080/',
    headers: { Authorization: sessionStorage.getItem(AUTH_HEADER_TOKEN) }
});