import React, { Component } from 'react';
import { Switch } from "react-router-dom";

import './mainViewContainer.css'
import Exercises from '../components/Exercises';
import LogoutComponent from '../components/auth/LogoutComponent';
import AuthenticatedRoute from '../helpers/auth/AuthenticatedRoute';

class MainViewContainer extends Component {
    render() {
        return (
            <div className='mainContent mainContainerStyle'>
                    <Switch>
                        <AuthenticatedRoute exact path='/' component={Exercises}/>
                        <AuthenticatedRoute path='/exercises' component={Exercises}/>
                        <AuthenticatedRoute path='/exercises/:exerciseId' component={Exercises}/>
                        <AuthenticatedRoute path='/logout' component={LogoutComponent}/>
                    </Switch>
            </div>
        );
    }
}

export default MainViewContainer;