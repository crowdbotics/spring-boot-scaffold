import React, { Component } from "react";

import AppContext from "../appContext";

class Home extends Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    if (!this.context.loggedIn) {
        this.props.history.push(`/login`);
    }
  }

  render() {
    return <h2>Welcome to spring-boot-scaffold app</h2>;
  }
}

Home.contextType = AppContext;

export default Home;
