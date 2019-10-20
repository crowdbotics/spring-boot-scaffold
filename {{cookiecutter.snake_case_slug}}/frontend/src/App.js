import React, { Component } from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";

import "./App.css";
import AppNavBar from "./component/layout/AppNavBar";
import Register from "./component/auth/Register";
import Login from "./component/auth/Login";
import Home from "./component/home";
import AppContext from "./component/appContext";
import { fetchLogin, fetchRegister } from "./service/Api";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

class App extends Component {
  constructor(props) {
    super(props);

    this.logIn = async (email, password) => {
      try {
        const loginResult = await fetchLogin(email, password);
        this.setState({
          loggedIn: loginResult.status,
          token: loginResult.token
        });

        return loginResult;
      } catch (error) {
        console.log(error);
      }
    };
    this.register = async (email, password, fname, lname) => {
      try {
        await fetchRegister(email, password, fname, lname);
        this.setState({
          loggedIn: false
        });
        toast("Register successfully.", { type: toast.TYPE.SUCCESS });
      } catch (error) {
        console.log(error);
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
      register: this.register
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
            <ToastContainer />
          </div>
        </Router>
      </AppContext.Provider>
    );
  }
}

export default App;
