import React, { Component } from 'react';
import { Modal, Button, Form } from 'react-bootstrap/';
import { Formik,  Field, ErrorMessage } from 'formik';

import api from '../helpers/Api';
import ErrorAlert from './helpers/ErrorAlert';
import handleError from '../helpers/ErrorHandlingService';


class AddExerciseModal extends Component {

    state = {
        show: false,
        errMsg: ''
    }
    
    handleShow = () => this.setState({ errMsg: '', show: true });
    handleClose = () => this.setState({ show: false });

    render() {
        return (
            <>
            <Button variant="outline-dark" onClick={this.handleShow}>Create new exercise</Button>
            <Modal show={this.state.show} onHide={this.handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Add exercise</Modal.Title>
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
            initialErrors={{ name: ''}}
            initialValues={{ name: '' }}
            validate={this.validateFields()}
            onSubmit={this.handleSubmit()}
            >
            {({ handleSubmit, errors, isSubmitting, isValid }) => (
                <Form>
                    <Field name='name'> 
                    {({ field }) => (
                        <Modal.Body>
                            <Form.Label className='labelStyle'>Name</Form.Label>
                            <Form.Control name="name" type="text" placeholder="Exercise name" 
                                value={field.value} onChange={field.onChange}/>
                            <div className='errorMsgStyle'>{errors.name}</div>
                            <ErrorMessage className='errorMsgStyle' name="name" component="div"/>
                        </Modal.Body>
                    )}
                    </Field>
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
            return errors;
        };
    }

    handleSubmit() {
        return (values, actions) => {
            this.setState({ errMsg: '' })
            api({
                method: 'POST',
                url: 'addExercise',
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

export default AddExerciseModal;