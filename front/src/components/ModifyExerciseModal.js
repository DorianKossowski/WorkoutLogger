import React, { Component } from 'react';
import { Modal, Button, Form } from 'react-bootstrap/';
import { Formik,  Field, ErrorMessage } from 'formik';
import { FaEdit } from 'react-icons/fa';

import api from '../helpers/Api';
import ErrorAlert from './helpers/ErrorAlert';
import handleError from '../helpers/ErrorHandlingService';


class ModifyExerciseModal extends Component {

    state = {
        show: false,
        errMsg: ''
    }
    
    handleShow = () => this.setState({ errMsg: '', show: true });
    handleClose = () => this.setState({ show: false });

    render() {
        return (
            <>
            <Button variant="outline-dark" onClick={this.handleShow}><FaEdit/> Modify</Button>
            <Modal show={this.state.show} onHide={this.handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Modify exercise</Modal.Title>
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
            initialValues={{ name: this.props.exercise.name }}
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
                            <ErrorMessage className='errorMsgStyle' name="name" component="div"/>
                        </Modal.Body>
                    )}
                    </Field>
                    <Modal.Footer>
                        <Button type='submit' onClick={handleSubmit} disabled={isSubmitting}>Modify</Button>
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
                method: 'PUT',
                url: `/exercises/edit/${ this.props.exercise.id }`,
                data: { ...values }
            })
            .then(() => {
                this.handleClose();
                this.props.postAction(values.name);
            }).catch((error) => {
                this.setState({ errMsg: handleError(error, 'Modification failed: ') });
                actions.setSubmitting(false);
            })
        };
    }
}

export default ModifyExerciseModal;