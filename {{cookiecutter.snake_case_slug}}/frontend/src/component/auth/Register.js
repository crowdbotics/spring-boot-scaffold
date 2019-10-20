import React, { Component } from "react";
import Alert from "../layout/Alert";
import AppContext from "../appContext";
import { theme } from "../../constant";

const emailRegEx =
  // eslint-disable-next-line max-len
  /^(([^<>()\\[\]\\.,;:\s@"]+(\.[^<>()\\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

class Register extends Component {
  state = {
    email: "",
    password: ""
  };

  componentWillMount() {}

  onSubmit = async e => {
    e.preventDefault();
    try {
      const { email, password, confirmPass, fname, lname } = this.state;

      if (!emailRegEx.test(email)) {
        this.setState({
          message: "Email is not valid!",
          messageType: "error"
        });
        return;
      }

      if (password.length < 6) {
        this.setState({
          message: "Password should be longer than 6 letters!",
          messageType: "error"
        });
        return;
      }

      if (password != confirmPass) {
        this.setState({
          message: "Confirm password does not match!",
          messageType: "error"
        });
        return;
      }

      await this.context.register(email, password, fname, lname);

      this.setState({
        message: "Register successfully.",
        messageType: "success"
      });


      this.props.history.push(`/login`);
    } catch (error) {
      console.log(error)
      this.setState({
        message: "Register failed",
        messageType: "error"
      });
    }
  };

  onChange = e => this.setState({ [e.target.name]: e.target.value });

  render() {
    const { message, messageType } = this.state;
    //cookie cutter does not want inline style
    const buttonStyle = { backgroundColor: "#330f77", color: "white" };

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
                  <i className="fas fa-lock" style={theme.primary} /> Register
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
                <div className="form-group">
                  <label htmlFor="password">Confirm Password</label>
                  <input
                    type="password"
                    className="form-control"
                    name="confirmPass"
                    required
                    value={this.state.confirmPass}
                    onChange={this.onChange}
                  />
                </div>
                {/* <div className="form-group">
                  <label htmlFor="email">First Name</label>
                  <input
                    type="text"
                    className="form-control"
                    name="fname"
                    required
                    value={this.state.fname}
                    onChange={this.onChange}
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="email">Last Name</label>
                  <input
                    type="text"
                    className="form-control"
                    name="lname"
                    required
                    value={this.state.lname}
                    onChange={this.onChange}
                  />
                </div> */}
                <input
                  type="submit"
                  value="Register"
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

Register.contextType = AppContext;

export default Register;
