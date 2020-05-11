import React, { Component } from 'react';

import api from '../helpers/Api.js';
import ExercisePanel from './panels/ExercisePanel';
import AddExerciseModal from './modals/AddExerciseModal';
import OvalLoader from './helpers/OvalLoader';
import ErrorAlert from './helpers/ErrorAlert';
import handleError from '../helpers/ErrorHandlingService';

class Exercises extends Component {
    
    state = {
        exercises : [],
        showModal: false,
        loading: false,
        errMsg: ''
    }

    componentDidMount() {
        this.getExercises();
    }

    getExercises = () => {
        this.setState({ loading: true });
        this.setState({ errMsg: '' });
        api({
            method: 'GET',
            url: 'exercises'
        })
        .then(data => this.setState({ exercises: data }))
        .catch(error => this.setState({ errMsg: handleError(error, 'Error during getting exercises: ') }))
        .finally(() => this.setState({ loading: false }));
    }

    getExercisesRender() {
        const mainContent = this.state.exercises.length === 0 ? 
            <div><p>Lack of exercises in database</p></div> :
            this.state.exercises.map(exercise => <ExercisePanel key={ exercise.id } data={ exercise }/>);
        return (
            <>
                {mainContent}
                Want more? <AddExerciseModal postAction={ this.getExercises }/>
            </>
        );
    }

    render() {
        return (
            <div>
                <ErrorAlert msg={this.state.errMsg}/>
                <h1>Exercises</h1>
                {this.state.loading ? <OvalLoader/> : this.getExercisesRender()}
            </div>
        );
    }
}

export default Exercises;