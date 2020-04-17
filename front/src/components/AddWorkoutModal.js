import React, { Component } from 'react';
import { Modal, Button, Form } from 'react-bootstrap/';
import { Formik,  Field, ErrorMessage } from 'formik';
import { Redirect } from 'react-router-dom';

import api from '../helpers/Api';
import ErrorAlert from './helpers/ErrorAlert';
import handleError from '../helpers/ErrorHandlingService';
import Checkbox from './helpers/Checkbox';


class AddWorkoutModal extends Component {

    state = {
        show: false,
        redirect: false,
        errMsg: ''
    }
    
    handleShow = () => this.setState({ errMsg: '', redirect: false, show: true });
    handleClose = () => this.setState({ show: false });

    render() {
        return (
            <>
            <Button variant="outline-dark" onClick={this.handleShow}>Create new workout</Button>
            <Modal show={this.state.show} onHide={this.handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Add workout</Modal.Title>
                </Modal.Header>
                {this.state.redirect ? <Redirect to={{ path: '/workouts', state: { refresh: true } }}/> : this.getCreationForm()}
            </Modal>
            </>
        );
    }

    getCreationForm() {
        return (
            <>
            <ErrorAlert msg={this.state.errMsg}/>
            <Formik
            initialErrors={{ name: '', exercisesId: ''}}
            initialValues={{ name: '', exercisesId: [] }}
            validate={this.validateFields()}
            onSubmit={this.handleSubmit()}
            >
            {({ handleSubmit, errors, isSubmitting, isValid }) => (
                <Form>    
                    <Modal.Body>
                        <Field name='name'> 
                        {({ field }) => (
                            <>
                            <Form.Label className='labelStyle'>Name</Form.Label>
                            <Form.Control name="name" type="text" placeholder="Workout name" 
                                value={field.value} onChange={field.onChange}/>
                            <div className='errorMsgStyle'>{errors.name}</div>
                            <ErrorMessage className='errorMsgStyle' name="name" component="div"/>
                            </>
                        )}
                        </Field>
                        <Form.Label className='labelStyle'>Exercises</Form.Label>
                        { this.props.exercises.map(exercise => <Checkbox key={exercise.id} name='exercisesId' 
                            value={exercise.name} id={exercise.id}/>) }
                        <div className='errorMsgStyle'>{errors.exercisesId}</div>
                        <ErrorMessage className='errorMsgStyle' name="exercisesId" component="div"/>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button type='submit' onClick={handleSubmit} disabled={isSubmitting || !isValid}>Create</Button>
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
            if(!values.name) {
                errors.name = 'Name is required';
            }
            if(!values.exercisesId.length) {
                errors.exercisesId = 'Select at least one exercise';
            }
            return errors;
        };
    }

    handleSubmit() {
        return (values, actions) => {
            this.setState({ errMsg: '' })
            api({
                method: 'POST',
                url: 'addWorkout',
                data: { ...values }
            })
            .then(() => {
                this.handleClose();
                this.setState({ redirect: true });
            }).catch((error) => {
                this.setState({ errMsg: handleError(error, 'Creation failed: ') });
                actions.setSubmitting(false);
            })
        };
    }
}

export default AddWorkoutModal;