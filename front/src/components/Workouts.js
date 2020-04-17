import React, { Component } from 'react';

import api from '../helpers/Api.js';
import OvalLoader from './helpers/OvalLoader';
import ErrorAlert from './helpers/ErrorAlert';
import AddWorkoutModal from './AddWorkoutModal.js';
import WorkoutPanel from './helpers/WorkoutPanel';

class Workouts extends Component {
    
    state = {
        exercises: [],
        workouts: [],
        showModal: false,
        loading: false,
        errMsg: ''
    }

    componentDidMount() {
        this.getWorkoutsWithExcercises();
    }
    
    componentDidUpdate() {
        const refresh = 'refresh';
        if(typeof this.props.location.state !== 'undefined' && this.props.location.state.hasOwnProperty(refresh)) {
            delete this.props.location.state;
            this.getWorkoutsWithExcercises();
        }
    }

    getWorkoutsWithExcercises() {
        this.setState({ loading: true });
        this.setState({ errMsg: '' });
        api({
            method: 'GET',
            url: 'workouts'
        })
        .then(data => this.setState({ workouts: data.workouts, exercises: data.exercises }))
        .catch(error => this.setState({ errMsg: error }))
        .finally(() => this.setState({ loading: false }));
    }

    getWorkoutsRender() {
        const mainContent = this.state.workouts.length === 0 ? 
            <div><p>Lack of workouts in database</p></div> :
            this.state.workouts.map(workout => <WorkoutPanel key={ workout.id } data={ workout }/>);
        return (
            <>
                {mainContent}
                Want more? <AddWorkoutModal exercises={ this.state.exercises }/>
            </>
        );
    }

    render() {
        return (
            <div>
                <ErrorAlert msg={this.state.errMsg}/>
                <h1>Workouts</h1>
                {this.state.loading ? <OvalLoader/> : this.getWorkoutsRender()}
            </div>
        );
    }
}

export default Workouts;