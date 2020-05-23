import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import { LineChart, Legend, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from "recharts";
import _ from 'lodash';

import OvalLoader from '../helpers/OvalLoader';
import ErrorAlert from '../helpers/ErrorAlert';

import api from '../../helpers/Api.js';
import handleError from '../../helpers/ErrorHandlingService';
import ModifySingleElementModal from '../modals/ModifySingleElementModal';
import DeleteSingleElementModal from '../modals/DeleteSingleElementModal.js';

import './singleElementStyle.css';
import ExerciseResultPanel from '../panels/ExerciseResultsPanel';

class Exercise extends Component {
    
    state = {
        exercise : [],
        results: [],
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
            url: `exercises/${ singleElementId }`
        })
        .then(data => this.setState({ exercise: data.exercise, results: data.results, chartInputs: data.chartInputs.map(input => input.data) }))
        .catch(error => this.setState({ errMsg: handleError(error, 'Error during getting: ') }))
        .finally(() => this.setState({ loading: false }));
    }

    updateName = (newName) => {
        this.setState( prevState => {
            let exercise = { ...prevState.exercise };
            exercise.name = newName;
            return { exercise };
        });
    }

    render() {
        return (
            <div>
                <ErrorAlert msg={this.state.errMsg}/>
                <h1>Exercise</h1>
                {this.state.loading ? <OvalLoader/> : this.getSingleElementRender()}
            </div>
        );
    }

    getSingleElementRender() {
        return (
            <>
            <div className='singleElementHeader'>
                <div className='singleElementHeaderLeft'>{this.state.exercise.name}</div>
                <div className='singleElementHeaderRight'>
                    <ModifySingleElementModal baseUrl={ this.getBaseUrl } postAction={ this.updateName } 
                        singleElement={this.state.exercise}/>
                    <DeleteSingleElementModal baseUrl={ this.getBaseUrl } postAction={ this.getRedirect } 
                        singleElement={this.state.exercise}/>
                </div>
            </div>
            { _.isEmpty(this.state.results) ? null : this.getResultsRender() }
            </>
        );
    }

    getResultsRender = () => {
        const dataKeyNames = new Set(this.state.results.map(result => result.name));
        return (
            <>
            <ResponsiveContainer width='100%' height={300}>
                <LineChart data={this.state.chartInputs} margin={{ top: 20, right: 20, left: 20, bottom: 20 }}>
                    <CartesianGrid strokeDasharray="3 3"/>
                    <XAxis dataKey="key" />
                    <YAxis type="number" domain={['auto', 'auto']} />
                    <Tooltip />
                    { [...dataKeyNames].map(dataKeyName => <Line type="monotone" dataKey={ dataKeyName } connectNulls={ true }/> ) }
                    <Legend />
                </LineChart>
            </ResponsiveContainer>
            <div>
            { this.state.results.map(result => <ExerciseResultPanel key={ result.id } data={ result }/>) } 
            </div>
            </>
        );
    }

    getBaseUrl = () => {
        return 'exercises';
    }

    getRedirect = () => {
        return (
            <Redirect to='/exercises'/>
        );
    }
}

export default Exercise;