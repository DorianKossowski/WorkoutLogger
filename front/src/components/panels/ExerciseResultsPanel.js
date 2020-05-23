import React, { Component } from 'react';
import { Container, Row, Col } from 'react-bootstrap';

import './panelStyle.css';

class ExerciseResultPanel extends Component {

    render() {
        return (
            <Container fluid className='workoutPanelWrapper'>
                <Row className='workoutPanelContentHeader'>
                    <Col xs lg className='workoutPanelContentSimple'>{ this.props.data.name } - { this.props.data.date }</Col>
                    <Col xs lg className='workoutPanelContentSimple'>Volume</Col>
                </Row>
                <Row className='workoutPanelContent'>
                    <Col xs lg className='workoutPanelContentSimple'>{ this.props.data.sets }</Col>
                    <Col xs lg className='workoutPanelContentSimple'>{ this.props.data.volume }</Col>
                </Row>
            </Container>
        );
    }

}

export default ExerciseResultPanel;