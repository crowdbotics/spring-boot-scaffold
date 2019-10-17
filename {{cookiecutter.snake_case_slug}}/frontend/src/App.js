import React, { Component } from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";

import "./App.css";
import AppNavBar from "./component/layout/AppNavBar";
import Register from "./component/auth/Register";
import Login from "./component/auth/Login";
import Home from "./component/home";
import AppContext from "./component/appContext";
import {fetchLogin, fetchRegister} from "./service/Api"

class App extends Component {
  constructor(props) {
    super(props);

    this.logIn = async (email, password) => {
      try {
        await fetchLogin(email, password)
        this.setState({
          loggedIn: true
        });
      } catch (error) {
        console.log(error)
      }
     
    };
    this.register = async (email, password, fname, lname) => {
      try {
        await fetchRegister(email, password, fname, lname)
        this.setState({
          loggedIn: true
        });
      } catch (error) {
        console.log(error)
      }
     
    };
    this.logOut = async () => {

      this.setState({
        loggedIn: false
      });
    };


    this.state = {
      loggedIn: false,

      logIn: this.logIn,
      logOut: this.logOut,
      register: this.register,
    };
  }

  render() {
    return (
      <AppContext.Provider value={this.state}>
        <Router>
          <div className="App">
            <AppNavBar />
            <div className="container">
              <Switch>
                <Route exact path={"/"} component={Home} />
                <Route exact path="/login" component={Login} />
                <Route exact path="/register" component={Register} />
              </Switch>
            </div>
          </div>
        </Router>
      </AppContext.Provider>
    );
  }
}

export default App;
