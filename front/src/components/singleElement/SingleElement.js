import React, { Component } from 'react';

import api from '../../helpers/Api.js';
import handleError from '../../helpers/ErrorHandlingService';
import ModifySingleElementModal from '../modals/ModifySingleElementModal';
import DeleteSingleElementModal from '../modals/DeleteSingleElementModal.js';

import './singleElementStyle.css';

class SingleElement extends Component {
    
    state = {
        singleElement : [],
        showModal: false,
        loading: false,
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
            url: `${ this.getUrlBase() }/${ singleElementId }`
        })
        .then(data => this.setState({ singleElement: data }))
        .catch(error => this.setState({ errMsg: handleError(error, 'Error during getting: ') }))
        .finally(() => this.setState({ loading: false }));
    }

    getUrlBase = () => {
        throw "Abstract method with base url not implemented";
    }

    getRedirect = () => {
        throw "Abstract method with base url not implemented";
    }

    updateName = (newName) => {
        this.setState( prevState => {
            let singleElement = { ...prevState.singleElement };
            singleElement.name = newName;
            return { singleElement };
        });
    }

    getSingleElementRender() {
        return (
            <>
            <div className='singleElementHeader'>
                <div className='singleElementHeaderLeft'>{this.state.singleElement.name}</div>
                <div className='singleElementHeaderRight'>
                    <ModifySingleElementModal baseUrl={ this.getUrlBase } postAction={ this.updateName } 
                        singleElement={this.state.singleElement}/>
                    <DeleteSingleElementModal baseUrl={ this.getUrlBase } postAction={ this.getRedirect } 
                        singleElement={this.state.singleElement}/>
                </div>
            </div>
            </>
        );
    }
}

export default SingleElement;