import api, { AUTH_HEADER_TOKEN } from '../Api';

export const USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser';

class AuthenticationService {

    isUserAuthenticated() {
        return this.getAuthenticatedUser() === null ? false : true;
    }

    getAuthenticatedUser() {
        return sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME);
    }

    executeBasicAuthenticationService(username, password) {
        return api({
                method: 'GET', 
                url: 'basicLogin',
                headers: { Authorization: this.createBasicAuthToken(username, password) } 
            });
    }

    createBasicAuthToken(username, password) {
        return 'Basic ' + window.btoa(username + ":" + password);
    }

    registerSuccessfulLogin(username, password) {
        sessionStorage.setItem(USER_NAME_SESSION_ATTRIBUTE_NAME, username);
        sessionStorage.setItem(AUTH_HEADER_TOKEN, this.createBasicAuthToken(username, password));
    }

    executeLogoutService() {
        api({
            method: 'POST',
            url: 'basicLogout'
        })
        .then(() => {
            sessionStorage.clear();
        })
    }
}

export default new AuthenticationService();