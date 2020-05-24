import React, { Component } from 'react'
import { Alert } from 'react-bootstrap';

import './alertStyle.css';

class SuccessAlert extends Component {

    render() {
        return (
            this.props.msg.length ? <Alert variant="success" className="alertStyle">{this.props.msg}</Alert> : null
        );
    }
}

export default SuccessAlert;