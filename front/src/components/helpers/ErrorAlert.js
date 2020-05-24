import React, { Component } from 'react'
import { Alert } from 'react-bootstrap';

import './alertStyle.css';

class ErrorAlert extends Component {

    render() {
        return (
            this.props.msg.length ? <Alert variant="danger" className="alertStyle">{this.props.msg}</Alert> : null
        );
    }
}

export default ErrorAlert;