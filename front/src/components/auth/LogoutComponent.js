import React, { Component } from 'react'
import { Redirect } from 'react-router-dom';

import AuthenticationService from '../../helpers/auth/AuthenticationService';

class LogoutComponent extends Component {

    state = {
        redirect: false,
        errMsg: ''
    }

    componentDidMount() {
        AuthenticationService.executeLogoutService();
    }

    render() {
        return (
            <div>
                <Redirect to='/login'/>
            </div>
        )
    }
}

export default LogoutComponent;