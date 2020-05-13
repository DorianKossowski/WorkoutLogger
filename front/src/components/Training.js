import React, { Component } from 'react';
import { Button } from 'react-bootstrap/';
import _ from 'lodash';

import api from '../helpers/Api.js';
import handleError from '../helpers/ErrorHandlingService';
import TrainingPanel from './panels/TrainingPanel';
import ErrorAlert from './helpers/ErrorAlert';
import OvalLoader from './helpers/OvalLoader';
import TrainingStateManager from './helpers/TrainingStateManager';

class Training extends Component {
    trainingStateManager = new TrainingStateManager();
    
    state = {
        exercises: [],
        showModal: false,
        loading: false,
        errMsg: '',
        trainingId: ''
    }

    componentDidMount = () => {
        this.getTraining();
    }

    getTraining = () => {
        const { workoutId, trainingId } = this.props.match.params;
        let getUrl = `workouts/${workoutId}/training/`;
        if(trainingId) {
            getUrl += trainingId;
        }
        this.setState({ loading: true });
        this.setState({ errMsg: '' });
        api({
            method: 'GET',
            url: getUrl
        })
        .then(data => this.setState({ trainingId: data.id, exercises: data.exercises }))
        .catch(error => this.setState({ errMsg: handleError(error, 'Error during getting: ') }))
        .finally(() => this.setState({ loading: false }));
    }

    getExercisesRender = () => {
        return (
            <>
            { this.state.exercises.map(exercise => <TrainingPanel key={ exercise.id } data={ exercise } postAction={ this.updateState }/>) }
            <Button variant="outline-dark" onClick={ this.applyState }>Apply</Button>
            <Button variant="outline-dark">Save</Button>
            </>
        );
    }

    updateState = (updatedSet) => {
        this.setState( prevState => {
            return { exercises: this.trainingStateManager.getUpdatedExercises(prevState.exercises, updatedSet) };
        });
    }

    applyState = () => {
        const { workoutId } = this.props.match.params;
        this.setState({ loading: true });
        this.setState({ errMsg: '' });
        
        let applyMethod;
        let applyUrl;
        
        if(this.state.trainingId) {
            applyMethod = 'PUT';
            applyUrl = `workouts/${ workoutId }/training/${ this.state.trainingId }`
        } else {
            applyMethod = 'POST';
            applyUrl = `workouts/${ workoutId }/addTraining`
        }
        api({
            method: applyMethod,
            url: applyUrl,
            data: { trainingId: this.state.trainingId, exercises: this.state.exercises }
        })
        .then(data => this.setState({ trainingId: data.id, exercises: data.exercises }))
        .catch(error => this.setState({ errMsg: handleError(error, 'Error during applying: ') }))
        .finally(() => this.setState({ loading: false }));  
    }

    render = () => {
        return (
            <div>
                <ErrorAlert msg={this.state.errMsg}/>
                <h1>Training</h1>
                {this.state.loading ? <OvalLoader/> : this.getExercisesRender()}
            </div>
        );
    }
}

export default Training;