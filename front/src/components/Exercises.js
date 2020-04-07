import React, { Component } from 'react';

import api from '../helpers/Api.js';
import GenericTable from './genericTable/GenericTable';
import AddExerciseModal from './AddExerciseModal';
import OvalLoader from './helpers/OvalLoader';

class Exercises extends Component {
    
    state = {
        exercises : [],
        showModal: false,
        loading: false
    }

    componentDidMount() {
        this.setState({ loading: true });
        api({
            method: 'GET',
            url: 'exercises'
        })
        .then(data => this.setState({exercises : data}))
        .catch(e => console.log('Get error: ', e))
        .finally(() => this.setState({ loading: false }));
    }

    getExercisesRender() {
        const mainContent = this.state.exercises.length === 0 ? 
            <div><p>Lack of exercises in database</p></div> :
            <GenericTable header={['Name']} rows={this.state.exercises} link={'/exercises/'}/>;
        return (
            <>
                {mainContent}
                Want more? <AddExerciseModal/>
            </>
        );
    }

    render() {
        return (
            <div>
                <h1>Exercises</h1>
                {this.state.loading ? <OvalLoader/> : this.getExercisesRender()}
            </div>
        );
    }
}

export default Exercises;