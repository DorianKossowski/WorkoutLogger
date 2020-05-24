import React, { Component } from 'react';
import { Button } from 'react-bootstrap/';
import _ from 'lodash';
import { Redirect } from 'react-router-dom';

import api from '../helpers/Api.js';
import handleError from '../helpers/ErrorHandlingService';
import TrainingPanel from './panels/TrainingPanel';
import ErrorAlert from './helpers/ErrorAlert';
import SuccessAlert from './helpers/SuccessAlert.js';
import OvalLoader from './helpers/OvalLoader';
import TrainingStateManager from './helpers/TrainingStateManager';
import DeleteSingleElementModal from './modals/DeleteSingleElementModal';

class Training extends Component {
    trainingStateManager = new TrainingStateManager();
    
    state = {
        exercises: [],
        showModal: false,
        loading: false,
        errMsg: '',
        successMsg: '',
        trainingId: '',
        trainingDate: '',
        redirect: false
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
        .then(data => this.setState({ trainingId: data.id, trainingDate: data.date, exercises: data.exercises }))
        .catch(error => this.setState({ errMsg: handleError(error, 'Error during getting: ') }))
        .finally(() => this.setState({ loading: false }));
    }

    getExercisesRender = () => {
        return (
            <>
            { this.state.trainingId ? this.renderHeader() : null }
            { _.isEmpty(this.state.exercises) ? null : this.renderContent() }
            </>
        );
    }

    renderContent = () => {
        return (
            <>
            { this.state.exercises.map(exercise => <TrainingPanel key={ exercise.id } data={ exercise } postAction={ this.updateState }/>) }
            <Button variant="outline-dark" onClick={ this.applyState }>Apply</Button>
            <Button variant="outline-dark" onClick={ this.saveState }>Save</Button>
            </>
        );
    }

    renderHeader = () => {
        return (
            <div className='singleElementHeader'>
                <div className='singleElementHeaderLeft'>Date: {this.state.trainingDate}</div>
                <div className='singleElementHeaderRight'>
                    <DeleteSingleElementModal baseUrl={ this.getUrlBase } postAction={ this.getRedirect } 
                        singleElement={{ id: this.state.trainingId }}/>
                </div>
            </div>
        );
    }

    getUrlBase = () => {
        return 'trainings';
    }

    getRedirect = () => {
        const { workoutId } = this.props.match.params;
        const redirectUrl = `/workouts/${ workoutId }`;
        return (
            <Redirect to={ redirectUrl }/>
        );
    }

    updateState = (updatedSet) => {
        this.setState( prevState => {
            return { exercises: this.trainingStateManager.getUpdatedExercises(prevState.exercises, updatedSet) };
        });
    }

    applyState = (save = false) => {
        const { workoutId } = this.props.match.params;
        this.setState({ loading: true });
        this.setState({ errMsg: '', successMsg: '' });
        
        let applyMethod;
        let applyUrl;
        
        if(this.state.trainingId) {
            applyMethod = 'PUT';
            applyUrl = `workouts/${ workoutId }/training/${ this.state.trainingId }`
        } else {
            applyMethod = 'POST';
            applyUrl = `workouts/${ workoutId }/addTraining`
        }
        return api({
            method: applyMethod,
            url: applyUrl,
            data: { id: this.state.trainingId, date: this.state.trainingDate, exercises: this.state.exercises }
        })
        .then(data => {
            this.setState({ trainingId: data.id, trainingDate: data.date, exercises: data.exercises });
            this.handleSuccess();
            if(save === true) {
                this.setState({ redirect: true });
            }
        })
        .catch(error => this.setState({ errMsg: handleError(error, 'Error during applying: ') }))
        .finally(() => this.setState({ loading: false }));  
    }

    saveState = () => {
        this.applyState(true)
    }

    handleSuccess = () => {
        this.setState({ successMsg: 'Applied correctly' });
        setTimeout(() => { this.setState({ successMsg: '' }) }, 2000);
    }

    render = () => {
        return (
            <div>
                <ErrorAlert msg={this.state.errMsg}/>
                <SuccessAlert msg={this.state.successMsg}/>
                <h1>Training</h1>
                { this.state.loading ? <OvalLoader/> : this.getExercisesRender() }
                { this.state.redirect ? this.getRedirect() : null }
            </div>
        );
    }
}

export default Training;