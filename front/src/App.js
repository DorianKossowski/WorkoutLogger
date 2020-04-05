import React, { Component } from 'react';
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import './index.css';

import LoginComponent from './components/LoginComponent';
import AuthorizedViewContainer from './containers/AuthorizedViewContainer';
import AuthenticatedRoute from './helpers/auth/AuthenticatedRoute';

class App extends Component {
    
  render() {
    return (
      <div className='fullHeight'>
        <Router>
          <Switch>
            <Route exact path='/login' component={LoginComponent}/>
            <AuthenticatedRoute component={AuthorizedViewContainer}/>
          </Switch>
        </Router>
      </div>
    );
  }
}

export default App;