import React, { Component } from 'react';
import { Modal, Button, Form } from 'react-bootstrap/';
import { Formik,  Field, ErrorMessage } from 'formik';

import api from '../../helpers/Api';
import ErrorAlert from '../helpers/ErrorAlert';
import handleError from '../../helpers/ErrorHandlingService';
import CheckBox from '../helpers/CheckBox';


class AddWorkoutModal extends Component {

    state = {
        show: false,
        errMsg: ''
    }
    
    handleShow = () => this.setState({ errMsg: '', show: true });
    handleClose = () => this.setState({ show: false });

    render() {
        return (
            <>
            <Button variant="outline-dark" onClick={this.handleShow}>Create new workout</Button>
            <Modal show={this.state.show} onHide={this.handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Add workout</Modal.Title>
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
                            <ErrorMessage className='errorMsgStyle' name="name" component="div"/>
                            </>
                        )}
                        </Field>
                        <Form.Label className='labelStyle'>Exercises</Form.Label>
                        { this.props.exercises.map(exercise => <CheckBox key={exercise.id} name='exercisesId' 
                            value={exercise.name} id={exercise.id}/>) }
                        <ErrorMessage className='errorMsgStyle' name="exercisesId" component="div"/>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button type='submit' onClick={handleSubmit} disabled={isSubmitting}>Create</Button>
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
                this.props.postAction();
            }).catch((error) => {
                this.setState({ errMsg: handleError(error, 'Creation failed: ') });
                actions.setSubmitting(false);
            })
        };
    }
}

export default AddWorkoutModal;