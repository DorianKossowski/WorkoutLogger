import api, { AUTH_HEADER_TOKEN } from '../Api';

export const USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser';

class AuthenticationService {

    isUserAuthenticated() {
        return this.getAuthenticatedUser() === null ? false : true;
    }

    getAuthenticatedUser() {
        return sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME);
    }

    executeBasicAuthenticationService(mail, password) {
        return api({
                method: 'GET', 
                url: 'basicLogin',
                headers: { Authorization: this.createBasicAuthToken(mail, password) } 
            });
    }

    createBasicAuthToken(mail, password) {
        return 'Basic ' + window.btoa(mail + ":" + password);
    }

    registerSuccessfulLogin(mail, password) {
        sessionStorage.setItem(USER_NAME_SESSION_ATTRIBUTE_NAME, mail);
        sessionStorage.setItem(AUTH_HEADER_TOKEN, this.createBasicAuthToken(mail, password));
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