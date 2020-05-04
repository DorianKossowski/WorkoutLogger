import React, { Component } from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import { Link } from 'react-router-dom';

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
                    <Col className='workoutPanelContentSimple'>placeholder</Col>
                    <Link to={ linkTo } className='clickableCol'><Col className='workoutPanelContentStats'>Manage</Col></Link>
                </Row>
            </Container>
        );
    }

}

export default ExercisePanel;