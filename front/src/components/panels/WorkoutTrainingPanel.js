import React, { Component } from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import { LinkContainer } from "react-router-bootstrap";

import './panelStyle.css';

class WorkoutTrainingPanel extends Component {

    render() {
        const linkToManage = `/workouts/${ this.props.data.workoutId }/training/${ this.props.data.training.id}`;
        return (
            <Container fluid className='workoutPanelWrapper'>
                <Row className='workoutPanelContent'>
                    <Col xs lg className='workoutPanelContentSimple'>Date: { this.props.data.training.date }</Col>
                    <LinkContainer to={ linkToManage }><Col xs lg as='a' className='workoutPanelContentClick'>Manage</Col></LinkContainer>
                </Row>
            </Container>
        );
    }

}

export default WorkoutTrainingPanel;