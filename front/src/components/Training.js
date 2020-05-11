import React, { Component } from 'react';
import { Button } from 'react-bootstrap/';
import _ from 'lodash';

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
        errMsg: ''
    }

    componentDidMount = () => {
        this.getExercises();
    }

    getExercises = () => {
        const { singleElementId } = this.props.match.params;
        // this.setState({ loading: true });
        this.setState({ errMsg: '' });
        this.setState({ exercises: [
            { id: 1, name: 'plecy', sets: [{ id:1, reps: 5, weight: 40 }, { id:2, reps: 8, weight: 30 }] }, 
            { id:2, name: 'klata', sets: [] }] 
        });
        // api({
        //     method: 'GET',
        //     url: `training/${ singleElementId }`
        // })
        // .then(data => this.setState({ singleElement: data }))
        // .catch(error => this.setState({ errMsg: handleError(error, 'Error during getting: ') }))
        // .finally(() => this.setState({ loading: false }));
    }

    updateState = (updatedSet) => {
        this.setState( prevState => {
            return { exercises: this.trainingStateManager.getUpdatedExercises(prevState.exercises, updatedSet) };
        });
    }

    getExercisesRender = () => {
        return (
            <>
            { this.state.exercises.map(exercise => <TrainingPanel key={ exercise.id } data={ exercise } postAction={ this.updateState }/>) }
            </>
        );
    }

    render = () => {
        return (
            <div>
                <ErrorAlert msg={this.state.errMsg}/>
                <h1>Training</h1>
                {this.state.loading ? <OvalLoader/> : this.getExercisesRender()}
                <Button variant="outline-dark">Apply</Button>
                <Button variant="outline-dark">Save</Button>
            </div>
        );
    }
}

export default Training;