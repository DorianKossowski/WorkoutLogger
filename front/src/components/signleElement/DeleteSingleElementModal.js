import React, { Component } from 'react';
import { Modal, Button, Form } from 'react-bootstrap/';
import { Formik } from 'formik';
import { FaTrash } from 'react-icons/fa';

import api from '../../helpers/Api';
import ErrorAlert from '../helpers/ErrorAlert';
import handleError from '../../helpers/ErrorHandlingService';


class DeleteSingleElementModal extends Component {

    state = {
        show: false,
        errMsg: '',
        redirect: false
    }
    
    handleShow = () => this.setState({ errMsg: '', show: true });
    handleClose = () => this.setState({ show: false });

    render() {
        return (
            <>
            { this.state.redirect ? this.props.postAction() : this.getDeletionModal() }   
            </>
        );
    }



    getDeletionModal() {
        return (
            <>
            <Button variant="outline-dark" onClick={this.handleShow}><FaTrash /> Delete</Button>
            <Modal show={this.state.show} onHide={this.handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Delete</Modal.Title>
                </Modal.Header>
                {this.getDeletionForm()}
            </Modal>
            </>
        );
    }

    getDeletionForm() {
        return (
            <>
            <ErrorAlert msg={this.state.errMsg}/>
            <Formik 
            initialErrors={{}}
            initialValues={{}}
            validator={() => ({})}
            onSubmit={this.handleSubmit()}>
            {({ handleSubmit, isSubmitting }) => (
                <Form>
                    <Modal.Body>
                    Are you sure?<br/>You are going to delete this element with all its content.
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant='danger' type='submit' onClick={handleSubmit} disabled={isSubmitting}>Delete</Button>
                    </Modal.Footer>
                </Form>
            )}
            </Formik>
            </>
        );
    }

    handleSubmit() {
        return (values, {setSubmitting}) => {
            this.setState({ errMsg: '' })
            api({
                method: 'DELETE',
                url: `/${ this.props.baseUrl() }/delete/${ this.props.singleElement.id }`,
            })
            .then(() => {
                this.handleClose();
                this.setState({ redirect: true });
            }).catch((error) => {
                this.setState({ errMsg: handleError(error, 'Deletion failed: ') });
                setSubmitting(false);
            })
        };
    }
}

export default DeleteSingleElementModal;