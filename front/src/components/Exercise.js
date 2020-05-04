import React, { Component } from 'react';

import api from '../helpers/Api.js';
import OvalLoader from './helpers/OvalLoader';
import ErrorAlert from './helpers/ErrorAlert';
import handleError from '../helpers/ErrorHandlingService';
import ModifyExerciseModal from './ModifyExerciseModal';

import './exerciseStyle.css';

class Exercise extends Component {
    
    state = {
        exercise : [],
        showModal: false,
        loading: false,
        errMsg: ''
    }

    componentDidMount() {
        this.getExercise();
    }

    getExercise = () => {
        const { exerciseId } = this.props.match.params;
        this.setState({ loading: true });
        this.setState({ errMsg: '' });
        api({
            method: 'GET',
            url: `exercises/${ exerciseId }`
        })
        .then(data => this.setState({ exercise: data }))
        .catch(error => this.setState({ errMsg: handleError(error, 'Error during getting exercise: ') }))
        .finally(() => this.setState({ loading: false }));
    }

    updateName = (newName) => {
        this.setState( prevState => {
            let exercise = { ...prevState.exercise };
            exercise.name = newName;
            return { exercise };
        });
    }

    getExerciseRender() {
        return (
            <>
                <div className='exerciseHeader'>
                    <div className='exerciseHeaderLeft'>{this.state.exercise.name}</div>
                    <div className='exerciseHeaderRight'>
                        <ModifyExerciseModal postAction={ this.updateName } exercise={this.state.exercise}/>
                    </div>
                </div>
            </>
        );
    }

    render() {
        return (
            <div>
                <ErrorAlert msg={this.state.errMsg}/>
                <h1>Exercise</h1>
                {this.state.loading ? <OvalLoader/> : this.getExerciseRender()}
            </div>
        );
    }
}

export default Exercise;