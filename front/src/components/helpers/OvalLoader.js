import React, { Component } from 'react'
import Loader from 'react-loader-spinner';

class OvalLoader extends Component {

    render() {
        return(
            <Loader type="Oval" color="#343a40" height={80} width={80}/>
        );
    }
}

export default OvalLoader;