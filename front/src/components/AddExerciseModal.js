import React, { Component } from 'react';
import { Modal, Button, Form } from 'react-bootstrap/';
import { Formik  } from 'formik';


class AddExerciseModal extends Component {

    state = {
        show: false
    }
    
    handleShow = () => this.setState({ show: true });
    handleClose = () => this.setState({ show: false });

    render() {
        return (
            <>
            <Button variant="outline-dark" onClick={this.handleShow}>Create new exercise</Button>
            <Modal show={this.state.show} onHide={this.handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Add exercise</Modal.Title>
                </Modal.Header>
                <Formik
                    onSubmit={this.handleSubmit()}>
                    {({ isSubmitting, isValid }) => (
                        <Form>
                            <Modal.Body>
                                <Form.Label>Name</Form.Label>
                                <Form.Control type="text" placeholder="Exercise name" required/>
                            </Modal.Body>
                            <Modal.Footer>
                                <Button type="submit" disabled={isSubmitting || !isValid} onClick={this.handleClose}>Create</Button>
                            </Modal.Footer>
                        </Form>
                    )}
                </Formik>
            </Modal>
          </>
        );
    }

    handleSubmit() {
        return (values, actions) => {
            // this.setState({ errMsg: '' });
            // api.post('coaches-add', { mail: values.mail, maxNumberOfTeams: values.numOfTeams })
            //     .then(() => this.setState({ redirect: true }))
            //     .catch(error => {
            //         this.setState({ errMsg: this.getErrorMsg(error) });
            //         actions.setSubmitting(false);
            //     });
        };
    }
}

export default AddExerciseModal;