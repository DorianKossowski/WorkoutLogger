import axios from 'axios';

export const AUTH_HEADER_TOKEN = 'authenticationToken';

const request = (options) => {
    const onSuccess = (response) => {
        console.debug('Request Successful!', response);
        return response.data;
      }
    
    const onError = (error) => {
        console.error('Request Failed:', error.config);

        if (error.response) {
            // Request was made but server responded with something
            // other than 2xx
            console.error('Status:',  error.response.status);
            console.error('Data:',    error.response.data);
            console.error('Headers:', error.response.headers);

        } else {
            // Something else happened while setting up the request
            // triggered the error
            console.error('Error Message:', error.message);
            sessionStorage.clear();
        }

        return Promise.reject(error.response || error.message);
    }

    const client = axios.create({
        baseURL: 'http://localhost:8080/',
        headers: { Authorization: sessionStorage.getItem(AUTH_HEADER_TOKEN) }
    });

    return client(options)
            .then(onSuccess)
            .catch(onError);
}

export default request;