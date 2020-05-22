import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';

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
        .then(data => this.setState({ exercise: data.exercise, results: data.results }))
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