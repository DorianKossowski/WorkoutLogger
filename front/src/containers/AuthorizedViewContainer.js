import React, { Component } from 'react';
import {Navbar, Nav, NavDropdown} from 'react-bootstrap/';
import { LinkContainer } from "react-router-bootstrap";

import './mainViewContainer.css'
import AuthenticationService from '../helpers/auth/AuthenticationService';
import MainViewContainer from './MainViewContainer';

class AuthorizedViewContainer extends Component {
    render() {
        return (
            <>
                <Navbar variant='dark' bg='dark' expand="lg">
                    <Navbar.Brand> 
                    <Navbar>Workout Logger</Navbar>
                    </Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className='mr-auto'>
                        <LinkContainer to='/'><Nav.Link>Home</Nav.Link></LinkContainer>
                        <LinkContainer to='/exercises'><Nav.Link>Exercises</Nav.Link></LinkContainer>
                        <LinkContainer to='/workouts'><Nav.Link>Workouts</Nav.Link></LinkContainer>
                    </Nav>
                    <Nav>
                        <NavDropdown title={"Hello " + AuthenticationService.getAuthenticatedUser()}  id="basic-nav-dropdown">
                        <NavDropdown.Item>Account</NavDropdown.Item>
                        <NavDropdown.Divider />
                        <LinkContainer to='/logout'><NavDropdown.Item>Log out</NavDropdown.Item></LinkContainer>
                        </NavDropdown>
                    </Nav>
                    </Navbar.Collapse>
                </Navbar>
                <MainViewContainer/>
            </>
        );
    }
}

export default AuthorizedViewContainer;