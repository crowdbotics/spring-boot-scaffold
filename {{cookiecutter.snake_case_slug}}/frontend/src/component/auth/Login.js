import React, { Component } from "react";
import Alert from "../layout/Alert";
import AppContext from "../appContext";
import { theme } from "../../constant";

const emailRegEx =
  // eslint-disable-next-line max-len
  /^(([^<>()\\[\]\\.,;:\s@"]+(\.[^<>()\\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

class Login extends Component {
  state = {
    email: "",
    password: ""
  };

  onSubmit = async e => {
    e.preventDefault();
    try {
      const { email, password } = this.state;

      if (!emailRegEx.test(email)) {
        this.setState({
          message: "Email is not valid!",
          messageType: "error"
        });
        return;
      }

      const result = await this.context.logIn(email, password);

      if (!result.status)
        this.setState({
          message: result.message,
          type: "error"
        });
      else {
        this.setState({
          message: "",
          messageType: ""
        });

        this.props.history.push(`/`);
      }
    } catch (error) {
      this.setState({
        message: "Login failed",
        messageType: "error"
      });
    }
  };

  onChange = e => this.setState({ [e.target.name]: e.target.value });

  render() {
    const { message, messageType } = this.state;
    return (
      <div className="row">
        <div className="col-md-6 mx-auto">
          <div className="card">
            <div className="card-body">
              {message ? (
                <Alert message={message} messageType={messageType} />
              ) : null}
              <h1 className="text-center pb-4 pt-3">
                <span style={theme.primary}>
                  <i className="fas fa-lock" style={theme.primary} /> Login
                </span>
              </h1>
              <form onSubmit={this.onSubmit}>
                <div className="form-group">
                  <label htmlFor="email">Email</label>
                  <input
                    type="text"
                    className="form-control"
                    name="email"
                    required
                    value={this.state.email}
                    onChange={this.onChange}
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="password">Password</label>
                  <input
                    type="password"
                    className="form-control"
                    name="password"
                    required
                    value={this.state.password}
                    onChange={this.onChange}
                  />
                </div>
                <input
                  type="submit"
                  value="Login"
                  className="btn btn-block"
                  style={theme.button}
                />
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

Login.contextType = AppContext;

export default Login;
