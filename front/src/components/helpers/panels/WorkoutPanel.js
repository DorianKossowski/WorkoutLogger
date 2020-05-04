import React, { Component } from 'react';
import { Container, Row, Col } from 'react-bootstrap';

import './panelStyle.css';

class WorkoutPanel extends Component {

    render() {
        return (
            <Container fluid className='workoutPanelWrapper'>
                <Row className='workoutPanelHeader'>
                    { this.props.data.name }
                </Row>
                <Row className='workoutPanelContent'>
                    <Col lg className='workoutPanelContentSimple'>{ this.getExercises(this.props.data.exercises) }</Col>
                    <Col xs lg='2' className='workoutPanelContentSimple'>placeholder</Col>
                    <Col xs lg='2' className='workoutPanelContentTrain'>Train</Col>
                    <Col xs lg='2' className='workoutPanelContentStats'>Manage</Col>
                </Row>
            </Container>
        );
    }

    getExercises(exrcsObjects) {
        return Object.values(exrcsObjects).map(obj => obj.name).join(", ");
    }
}

export default WorkoutPanel;