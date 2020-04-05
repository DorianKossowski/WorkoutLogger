import React, { Component } from 'react';
import { Link } from "react-router-dom";
import {Navbar, Nav, NavDropdown} from 'react-bootstrap/';

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
                        <Nav.Link as='div'><Link to='/'>Home</Link></Nav.Link>
                        <Nav.Link as='div'><Link to='/exercises'>Exercises</Link></Nav.Link>
                        <Nav.Link as='div'><Link to='/workout'>Workout</Link></Nav.Link>
                    </Nav>
                    <Nav>
                        <NavDropdown title={"Hello " + AuthenticationService.getAuthenticatedUser()}  id="basic-nav-dropdown" className="justify-content-end">
                        <NavDropdown.Item>Account</NavDropdown.Item>
                        <NavDropdown.Divider />
                        <NavDropdown.Item as='div'><Link to='/logout'>Log out</Link></NavDropdown.Item>
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