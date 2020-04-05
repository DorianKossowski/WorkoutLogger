import React, { Component } from 'react';

import Api from '../helpers/Api.js';
import GenericTable from './genericTable/GenericTable';
import AddExerciseModal from './AddExerciseModal';

class Exercises extends Component {
    
    state = {
        exercises : [],
        showModal: false
    }

    componentDidMount() {
        Api.get('exercises')
        .then(response => response.data)
        .then(data => this.setState({exercises : data}))
        .catch(e => console.log('Get error: ', e));
    }

    getExercisesRender() {
        if(this.state.exercises.length === 0) {
            return (
                <div>
                    <p>Lack of exercises in database</p>
                </div>
            );
        }
        return ( 
            <GenericTable header={['Name']} rows={this.state.exercises} link={'/exercises/'}/>
        );
    }

    render() {
        return (
            <div>
                <h1>Exercises</h1>
                {this.getExercisesRender()}
                Want more? <AddExerciseModal/>
            </div>
        );
    }
}

export default Exercises;