import React from "react";
import spinner from "./spinner.gif";

export default () => {
  return (
    <div>
      <img src={spinner} alt="Loading..." style={styles.spinner} />
    </div>
  );
};

const styles = {
  spinner: { width: "200px", margin: "auto", display: "block" }
};
