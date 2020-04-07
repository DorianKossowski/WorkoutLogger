import React, { Component } from 'react'
import { Alert } from 'react-bootstrap';

class ErrorAlert extends Component {

    render() {
        return (
            this.props.msg.length ? <Alert variant="danger">{this.props.msg}</Alert> : null
        );
    }
}

export default ErrorAlert;