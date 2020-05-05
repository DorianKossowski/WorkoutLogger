import React, { Component } from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import { LinkContainer } from "react-router-bootstrap";

import './panelStyle.css';

class WorkoutPanel extends Component {

    render() {
        const linkTo = '/workouts/' + this.props.data.id;
        return (
            <Container fluid className='workoutPanelWrapper'>
                <Row className='workoutPanelHeader'>
                    { this.props.data.name }
                </Row>
                <Row className='workoutPanelContent'>
                    <Col lg className='workoutPanelContentSimple'>{ this.getExercises(this.props.data.exercises) }</Col>
                    <Col xs lg='2' className='workoutPanelContentSimple'>Last: 05.05.2020</Col>
                    <Col xs lg='2' className='workoutPanelContentTrain'>Train</Col>
                    <LinkContainer to={ linkTo }><Col xs lg='2' as='a' className='workoutPanelContentStats'>Manage</Col></LinkContainer>
                </Row>
            </Container>
        );
    }

    getExercises(exrcsObjects) {
        return Object.values(exrcsObjects).map(obj => obj.name).join(", ");
    }
}

export default WorkoutPanel;