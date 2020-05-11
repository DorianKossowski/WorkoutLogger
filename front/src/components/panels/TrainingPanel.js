import React, { Component } from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import AddSetModal from '../modals/AddSetModal';

import './panelStyle.css';
import ModifySetModal from '../modals/ModifySetModal';

class TrainingPanel extends Component {

    render() {
        return (
            <Container fluid className='workoutPanelWrapper'>
                <Row className='workoutPanelHeader'>
                    { this.props.data.name }
                </Row>
                <Row className='workoutPanelContent'>
                    <Col lg className='displayInherit'>
                        {this.props.data.sets.map(set => <ModifySetModal key={ `set${set.id}` } exerciseId={ this.props.data.id }
                            data={ set } postAction={ this.props.postAction }/>)}
                    </Col>
                    <AddSetModal exerciseId={ this.props.data.id } postAction={ this.props.postAction }/>
                </Row>
            </Container>
        );
    }

}

export default TrainingPanel;