import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import { LineChart, Legend, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';
import _ from 'lodash'

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
        chartInputs: [],
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
        .then(data => this.setState({ workout: data.workout, trainings: data.trainings, chartInputs: data.chartInputs.map(input => input.data) }))
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
            { _.isEmpty(this.state.trainings) ? null : this.getResultsRender() }
            </>
        );
    }

    getResultsRender = () => {
        const dataKeyNames = new Set(this.state.workout.exercises.map(exercise => exercise.name));
        let values = [];
        this.state.chartInputs.forEach(input => values.push(
            parseFloat(input[_.maxBy(_.keys(input), (k) => k === 'key' ? 0 : parseFloat(input[k]))])
        ));
        return (
            <>
            <ResponsiveContainer width='100%' height={300}>
                <LineChart data={this.state.chartInputs} margin={{ top: 20, right: 20, left: 20, bottom: 20 }}>
                    <CartesianGrid strokeDasharray='3 3'/>
                    <XAxis dataKey='key' />
                    <YAxis type='number' domain={['auto', _.max(values)]}/>
                    <Tooltip />
                    { [...dataKeyNames].map(dataKeyName => <Line key={ `line_${dataKeyName}` } stroke={ this.getRandomColor() } dataKey={ dataKeyName } connectNulls={ true }/> ) }
                    <Legend />
                </LineChart>
            </ResponsiveContainer>
            <div>
            { this.state.trainings.map(training => <WorkoutTrainingPanel key={ training.id } 
                data={{ workoutId: this.state.workout.id, training: training }}/>) } 
            </div>
            </>
        );
    }

    getRandomColor = () => {
        var letters = '0123456789ABCDEF';
        var color = '#';
        for (var i = 0; i < 6; i++) {
          color += letters[Math.floor(Math.random() * 16)];
        }
        return color;
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