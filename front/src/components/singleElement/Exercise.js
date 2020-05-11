import React from 'react';
import { Redirect } from 'react-router-dom';

import SingleElement from './SingleElement';
import OvalLoader from '../helpers/OvalLoader';
import ErrorAlert from '../helpers/ErrorAlert';

class Exercise extends SingleElement {
    
    getUrlBase = () => {
        return 'exercises';
    }

    getRedirect = () => {
        return (
            <Redirect to='/exercises'/>
        );
    }

    render() {
        return (
            <div>
                <ErrorAlert msg={this.state.errMsg}/>
                <h1>Exercise</h1>
                {this.state.loading ? <OvalLoader/> : this.getSingleElementRender()}
            </div>
        );
    }
}

export default Exercise;