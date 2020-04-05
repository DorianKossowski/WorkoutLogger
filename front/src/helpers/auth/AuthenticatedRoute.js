import React, { Component } from 'react';
import { Route, Redirect } from "react-router-dom";


import AuthenticationService from './AuthenticationService';

class AuthenticatedRoute extends Component{

    render() {
        return (
            AuthenticationService.isUserAuthenticated() ? <Route {...this.props}/> : <Redirect to='/login'/>
        );
    }

}

export default AuthenticatedRoute;