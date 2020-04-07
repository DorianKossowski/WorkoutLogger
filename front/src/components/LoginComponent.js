import React, { Component } from 'react'
import { Formik, Form, Field, ErrorMessage } from 'formik';
import { Button } from 'react-bootstrap';
import { Redirect } from 'react-router-dom';

import AuthenticationService from '../helpers/auth/AuthenticationService';
import ErrorAlert from './helpers/ErrorAlert';

class LoginComponent extends Component {

    state = {
        redirect: false,
        errMsg: ''
    }

    handleSubmit() {
        return (values, actions) => {
            this.setState({ errMsg: '' })
            AuthenticationService.executeBasicAuthenticationService(values.mail, values.password)
            .then(() => {
                AuthenticationService.registerSuccessfulLogin(values.mail, values.password);
                this.setState({ redirect: true });
            }).catch((error) => {
                this.setState({ errMsg: 'Login failed: ' + error });
                actions.setSubmitting(false);
            })
        };
    }

    getErrorMsg(error) {
        return  error.response ? error.response.data.message : error.message;
    }

    validateFields() {
        return (values) => {
            const errors = {};
            if(!values.mail) {
                errors.mail = 'Mail is required';
            }
            if(!values.password) {
                errors.password = 'Password is required';
            }
            return errors;
        };
    }

    render() {
        return (
            <div className='authFormStyle'>
                <div className='authFormLogoStyle'>
                    <h1>Workout Logger</h1>
                </div>
                {this.state.redirect ? <Redirect to='/'/> : this.getLoginForm()}
            </div>
        )
    }

    getLoginForm() {
        return (
            <>
            <ErrorAlert msg={this.state.errMsg}/>
            <div className='authFormContentStyle'>
                <h2>Login</h2>
                <Formik 
                    initialErrors={{ mail: 'Required', password: 'Required' }} 
                    initialValues={{ mail: '', password: '' }} 
                    validate={this.validateFields()} 
                    onSubmit={this.handleSubmit()}
                >
                    {({ isSubmitting, isValid }) => (<Form>
                        <div className='formGroupStyle'>
                            <h6>Mail</h6>
                            <Field className='form-control' name="mail" type="text" placeholder="mail" required />
                            <ErrorMessage className='errorMsgStyle' name="mail" component="div" />
                        </div>
                        <div className='formGroupStyle'>
                            <h6>Password</h6>
                            <Field className='form-control' name='password' type="password" placeholder="password" />
                            <ErrorMessage className='errorMsgStyle' name="password" component="div" />
                        </div>
                        <Button type="submit" disabled={isSubmitting || !isValid}>Submit</Button>
                    </Form>)}
                </Formik>
            </div>
            </>
        );
    }
}

export default LoginComponent;