import React, { Component } from 'react';
import { Modal, Button, Form } from 'react-bootstrap/';
import { Formik,  Field, ErrorMessage } from 'formik';
import { Col } from 'react-bootstrap';

import api from '../../helpers/Api';
import ErrorAlert from '../helpers/ErrorAlert';
import handleError from '../../helpers/ErrorHandlingService';

import '../panels/panelStyle.css';
import './modalStyle.css';


class AddSetModal extends Component {

    state = {
        show: false,
        errMsg: ''
    }
    
    handleShow = () => this.setState({ errMsg: '', show: true });
    handleClose = () => this.setState({ show: false });

    render() {
        return (
            <>
            <Col xs lg='2' onClick={this.handleShow} className='workoutPanelContentClick'>Log</Col>
            <Modal show={this.state.show} onHide={this.handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Log</Modal.Title>
                </Modal.Header>
                { this.getCreationForm() }
            </Modal>
            </>
        );
    }

    getCreationForm() {
        return (
            <>
            <ErrorAlert msg={this.state.errMsg}/>
            <Formik
            initialErrors={{ reps: '', weight: '' }}
            initialValues={{ reps: '', weight: '' }}
            validate={this.validateFields()}
            onSubmit={this.handleSubmit()}
            >
            {({ handleSubmit, isSubmitting }) => (
                <Form>
                    <Modal.Body>
                        <Form.Label className='labelStyle'>Result</Form.Label>
                        <div className='addSetWrapper'>
                            <Field name='reps'> 
                            {({ field }) => (
                                <>
                                <Form.Control className='addSetField' name="reps" type="number" placeholder="Reps" 
                                    value={field.value} onChange={field.onChange}/>
                                </>
                            )}
                            </Field>
                            <p className='addSetX'>X</p>
                            <Field name='weight'> 
                            {({ field }) => (
                                <>
                                <Form.Control className='addSetField' name="weight" type="number" placeholder="Weight [kg]" 
                                    value={field.value} onChange={field.onChange}/>
                                </>
                            )}
                            </Field>
                            <ErrorMessage className='errorMsgStyle' name="reps" component="div"/>
                            <ErrorMessage className='errorMsgStyle' name="weight" component="div"/>
                        </div>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button type='submit' onClick={handleSubmit} disabled={isSubmitting}>Log</Button>
                    </Modal.Footer>
                </Form>
            )}
            </Formik>
            </>
        );
    }

    validateFields() {
        return (values) => {
            const errors = {};
            if(!values.reps) {
                errors.reps = 'Reps is required';
            } else if(values.reps < 0) {
                errors.reps = 'Reps has to be positive';
            }
            if(!values.weight) {
                errors.weight = 'Weight is required';
            } else if(values.weight < 0) {
                errors.weight = 'Weight has to be positive';
            }
            return errors;
        };
    }

    handleSubmit() {
        return (values) => {
            this.handleClose();
            this.props.postAction({ exerciseId: this.props.exerciseId, set: { ...values } });
        }
    }
}

export default AddSetModal;