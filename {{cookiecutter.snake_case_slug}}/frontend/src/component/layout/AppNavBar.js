import React, { Component } from "react";
import { Link } from "react-router-dom";
import Spinner from "../layout/Spinner";
import AppContext from "../appContext";
import { theme } from "../../constant";

class AppNavBar extends Component {
  onLogOut = async () => {
    try {
      await this.context.logOut();
      window.location.reload();
    } catch (error) {
      console.log(error);
    }
  };

  

  render() {
    const { loggedIn } = this.context
    return (
      <nav
        className="navbar navbar-expand-md navbar-dark mb-4"
        style={theme.background}
      >
        <div className="container">
          <Link to="/" className="navbar-brand">
            Crowdbotics
          </Link>

          <button
            className="navbar-toggler"
            type="button"
            data-toggle="collapse"
            data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon" />
          </button>

          <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <ul className="navbar-nav mr-auto">
              <li className="nav-item active">
                <Link to="/" className="nav-link">
                  Home <span className="sr-only">(current)</span>
                </Link>
              </li>
            </ul>
            {loggedIn ? (
              <a
                onClick={this.onLogOut}
                className="btn btn-bd-download d-none d-lg-inline-block mb-3 mb-md-0 ml-md-3 text-white"
              >
                Log out
              </a>
            ) : null}

            {!loggedIn ? (
              <ul className="navbar-nav ml-auto">
                <li className="nav-item">
                  <Link to="/login" className="nav-link">
                    Login
                  </Link>
                </li>
                <li className="nav-item">
                  <Link to="/register" className="nav-link">
                    Register
                  </Link>
                </li>
              </ul>
            ) : null}
          </div>
        </div>
      </nav>
    );
  }
}

AppNavBar.contextType = AppContext;

export default AppNavBar;
