import React, { Component } from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import { LinkContainer } from "react-router-bootstrap";

import './panelStyle.css';

class ExercisePanel extends Component {

    render() {
        const linkTo = '/exercises/' + this.props.data.id;
        return (
            <Container fluid className='workoutPanelWrapper'>
                <Row className='workoutPanelHeader'>
                    { this.props.data.name }
                </Row>
                <Row className='workoutPanelContent'>
                    <Col className='workoutPanelContentSimple'>Last: 05.05.2020</Col>
                    <LinkContainer to={ linkTo }><Col as='a' className='workoutPanelContentClick'>Manage</Col></LinkContainer>
                </Row>
            </Container>
        );
    }

}

export default ExercisePanel;