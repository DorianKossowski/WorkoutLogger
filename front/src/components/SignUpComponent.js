import React, { Component } from 'react'
import { Formik, Form, Field, ErrorMessage } from 'formik';
import { Button } from 'react-bootstrap';
import { Link, Redirect } from 'react-router-dom';

import api from '../helpers/Api';
import ErrorAlert from './helpers/ErrorAlert';
import handleError from '../helpers/ErrorHandlingService';

class SignUpCompnent extends Component {

    state = {
        redirect: false,
        errMsg: ''
    }

    handleSubmit() {
        return (values, actions) => {
            this.setState({ errMsg: '' })
            api({
                method: 'POST',
                url: 'signup',
                data: { ...values }
            })
            .then(() => {
                this.setState({ redirect: true });
            }).catch((error) => {
                this.setState({ errMsg: handleError(error, 'Sign up failed: ') });
                actions.setSubmitting(false);
            })
        };
    }

    validateFields() {
        return (values) => {
            const errors = {};
            if(!values.mail) {
                errors.mail = 'Mail is required';
            }
            if(!values.username) {
                errors.username = 'Username is required';
            }
            if(!values.password || !values.repPassword) {
                errors.repPassword = 'Passwords are required';
            } else if(values.password !== values.repPassword) {
                errors.repPassword = 'Passwords are different';
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
                {this.state.redirect ? <Redirect to='/login'/> : this.getLoginForm()}
                <div className='dontHaveAccountStyle'>Already have an account? <Link to='/login'>Log in</Link></div>
            </div>
        )
    }

    getLoginForm() {
        return (
            <>
            <ErrorAlert msg={this.state.errMsg}/>
            <div className='authFormContentStyle'>
                <h2>Sign up</h2>
                <Formik 
                    initialErrors={{ mail: 'Required', username: 'Required', repPassword: 'Required' }} 
                    initialValues={{ mail: '', username: '', repPassword: '' }} 
                    validate={this.validateFields()} 
                    onSubmit={this.handleSubmit()}
                >
                    {({ isSubmitting, isValid }) => (<Form>
                        <div className='formGroupStyle'>
                            <h6>Mail</h6>
                            <Field className='form-control' name="mail" type="email" placeholder="mail"/>
                            <ErrorMessage className='errorMsgStyle' name="mail" component="div"/>
                        </div>
                        <div className='formGroupStyle'>
                            <h6>Username</h6>
                            <Field className='form-control' name="username" type="text" placeholder="username"/>
                            <ErrorMessage className='errorMsgStyle' name="username" component="div"/>
                        </div>
                        <div className='formGroupStyle'>
                            <h6>Password</h6>
                            <Field className='form-control' name='password' type="password" placeholder="password"/>
                        </div>
                        <div className='formGroupStyle'>
                            <h6>Repeat password</h6>
                            <Field className='form-control' name='repPassword' type="password" placeholder="password"/>
                            <ErrorMessage className='errorMsgStyle' name="repPassword" component="div"/>
                        </div>
                        <Button type="submit" disabled={isSubmitting || !isValid}>Submit</Button>
                    </Form>)}
                </Formik>
            </div>
            </>
        );
    }
}

export default SignUpCompnent;