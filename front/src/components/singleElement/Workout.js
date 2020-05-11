import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';

import api from '../../helpers/Api.js';
import handleError from '../../helpers/ErrorHandlingService';
import ModifySingleElementModal from '../modals/ModifySingleElementModal';
import DeleteSingleElementModal from '../modals/DeleteSingleElementModal.js';
import OvalLoader from '../helpers/OvalLoader';
import ErrorAlert from '../helpers/ErrorAlert';
import WorkoutTrainingPanel from '../panels/WorkoutTrainingPanel';

class Workout extends Component {

    state = {
        workout : [],
        trainings: [],
        showModal: false,
        loading: true,
        errMsg: ''
    }

    componentDidMount = () => {
        this.getSingleElement();
    }

    getSingleElement = () => {
        const { singleElementId } = this.props.match.params;
        this.setState({ loading: true });
        this.setState({ errMsg: '' });
        api({
            method: 'GET',
            url: `workouts/${ singleElementId }`
        })
        .then(data => this.setState({ workout: data.workout, trainings: data.trainings }))
        .catch(error => this.setState({ errMsg: handleError(error, 'Error during getting: ') }))
        .finally(() => this.setState({ loading: false }));
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

    getSingleElementRender() {
        return (
            <>
            <div className='singleElementHeader'>
                <div className='singleElementHeaderLeft'>{this.state.workout.name}</div>
                <div className='singleElementHeaderRight'>
                    <ModifySingleElementModal baseUrl={ this.getBaseUrl } postAction={ this.updateName } 
                        singleElement={this.state.workout}/>
                    <DeleteSingleElementModal baseUrl={ this.getBaseUrl } postAction={ this.getRedirect } 
                        singleElement={this.state.workout}/>
                </div>
            </div>
            <div>
            { this.state.trainings.map(training => <WorkoutTrainingPanel key={ training.id } 
                data={{ workoutId: this.state.workout.id, training: training }}/>) } 
            </div>
            </>
        );
    }

    updateName = (newName) => {
        this.setState( prevState => {
            let workout = { ...prevState.workout };
            workout.name = newName;
            return { workout };
        });
    }

    getBaseUrl = () => {
        return 'workouts';
    }

    getRedirect = () => {
        return (
            <Redirect to='/workouts'/>
        );
    }
}

export default Workout;