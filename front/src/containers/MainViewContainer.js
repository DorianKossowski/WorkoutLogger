import React, { Component } from 'react';
import { Switch } from "react-router-dom";

import './mainViewContainer.css'
import Exercises from '../components/Exercises';
import Exercise from '../components/signleElement/Exercise';
import Workout from '../components/signleElement/Workout';
import Workouts from '../components/Workouts';
import LogoutComponent from '../components/auth/LogoutComponent';
import AuthenticatedRoute from '../helpers/auth/AuthenticatedRoute';

class MainViewContainer extends Component {
    render() {
        return (
            <div className='mainContent mainContainerStyle'>
                    <Switch>
                        <AuthenticatedRoute exact path='/' component={ Exercises }/>
                        <AuthenticatedRoute path='/exercises/:singleElementId' component={ Exercise }/>
                        <AuthenticatedRoute path='/exercises' component={ Exercises }/>
                        <AuthenticatedRoute path='/workouts/:singleElementId' component={ Workout }/>
                        <AuthenticatedRoute path='/workouts' component={ Workouts }/>
                        <AuthenticatedRoute path='/logout' component={ LogoutComponent }/>
                    </Switch>
            </div>
        );
    }
}

export default MainViewContainer;