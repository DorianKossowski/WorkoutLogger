import React from 'react';
import { Redirect } from 'react-router-dom';

import OvalLoader from '../helpers/OvalLoader';
import ErrorAlert from '../helpers/ErrorAlert';

import SingleElement from './SingleElement.js';

class Workout extends SingleElement {

    getUrlBase = () => {
        return 'workouts';
    }

    getRedirect = () => {
        return (
            <Redirect to='/workouts'/>
        );
    }

    render() {
        return (
            <div>
                <ErrorAlert msg={this.state.errMsg}/>
                <h1>Workout</h1>
                {this.state.loading ? <OvalLoader/> : this.getSingleElementRender()}
            </div>
        );
    }
}

export default Workout;