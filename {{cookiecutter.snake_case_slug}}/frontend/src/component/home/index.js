import React, { Component } from "react";
import AppContext from "../appContext";
import {
  fetchGetTasks,
  fetchCreateTask,
  fetchDeleteTask,
  fetchUpdateTasks
} from "../../service/Api";
import "./index.css";

class Home extends Component {
  constructor(props) {
    super(props);

    this.state = {
      tasks: [],
      task: "",
      updTask: "",
      taskId: 0
    };
  }

  async componentDidMount() {
    try {
      if (!this.context.loggedIn) {
        this.props.history.push(`/login`);
      }

      this.reloadTasks();
    } catch (error) {
      console.log(error);
    }
  }

  reloadTasks = async () => {
    const getTasksResult = await fetchGetTasks(this.context.token);
    this.setState({ tasks: getTasksResult.data });
  };

  createTask = async () => {
    try {
      const createTaskResult = await fetchCreateTask(
        this.context.token,
        this.state.task
      );

      this.reloadTasks();

      this.setState({
        task: ""
      });
    } catch (error) {
      console.log(error);
    }
  };

  deleteTask = async id => {
    try {
      const deleteTaskResult = await fetchDeleteTask(this.context.token, id);

      this.reloadTasks();

      this.setState({
        task: ""
      });
    } catch (error) {
      console.log(error);
    }
  };

  selectTask = id => {
    this.setState({
      taskId: id,
      updTask: this.state.tasks.filter(i => i.id == id)[0].description
    });
  };

  updateTask = async () => {
    try {
      const updateTaskResult = await fetchUpdateTasks(
        this.context.token,
        this.state.taskId,
        this.state.updTask
      );

      this.reloadTasks();

      this.setState({
        taskId: 0
      });
    } catch (error) {
      console.log(error);
    }
  };

  onChange = e => this.setState({ [e.target.name]: e.target.value });

  render() {
    const { tasks } = this.state;
    return (
      <div className="page-content page-container" id="page-content">
        <div className="padding">
          <div className="row container d-flex justify-content-center">
            <div className="col-lg-12">
              <div className="card px-3">
                <div className="card-body">
                  <h2 className="card-title">A Task Manager</h2>
                  <div className="add-items d-flex">
                    {" "}
                    <input
                      type="text"
                      name="task"
                      id="task"
                      className="form-control todo-list-input"
                      placeholder="What do you need to do today?"
                      onChange={this.onChange}
                      value={this.state.task}
                    />{" "}
                    <button
                      onClick={this.createTask}
                      className="add btn font-weight-bold todo-list-add-btn"
                    >
                      Add
                    </button>{" "}
                  </div>
                  <div className="list-wrapper">
                    <ul className="d-flex flex-column-reverse todo-list">
                      {tasks ? (
                        tasks.map((item, i) => (
                          <li key={item.id}>
                            <div className="form-check">
                              <label className="form-check-label">
                                {item.description + " "}
                                <i className="input-helper"></i>
                              </label>{" "}
                            </div>{" "}
                            <div className="remove d-flex">
                              <i
                                className="fas fa-edit remove"
                                onClick={this.selectTask.bind(this, item.id)}
                                data-toggle="modal"
                                data-target=".bd-buy-credit-modal"
                              ></i>
                              {"  "}
                              <i
                                className="fas fa-trash-alt remove"
                                onClick={() => {
                                  if (
                                    window.confirm(
                                      "Are you sure you want to delete this task?"
                                    )
                                  ) {
                                    this.deleteTask(item.id);
                                  }
                                }}
                              ></i>
                            </div>
                          </li>
                        ))
                      ) : (
                        <React.Fragment />
                      )}
                    </ul>
                  </div>
                  <div
                    class="modal fade bd-buy-credit-modal"
                    tabindex="-1"
                    role="dialog"
                    aria-labelledby="myLargeModalLabel"
                    aria-hidden="true"
                  >
                    <div class="modal-dialog modal-sm">
                      <div class="modal-content">
                        <div class="modal-header">
                          <h4 class="modal-title" id="myLargeModalLabel">
                            Edit Task
                          </h4>
                          <button
                            type="button"
                            class="close"
                            data-dismiss="modal"
                            aria-label="Close"
                          >
                            <span aria-hidden="true">×</span>
                          </button>
                        </div>
                        <div class="modal-body ">
                          <div className="form-group mt-2">
                            <input
                              type="text"
                              className="form-control"
                              name="updTask"
                              value={this.state.updTask}
                              onChange={this.onChange}
                            />
                          </div>
                          <button
                            onClick={this.updateTask}
                            className="add btn-block btn font-weight-bold todo-list-add-btn"
                            data-dismiss="modal"
                          >
                            Update
                          </button>{" "}
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

Home.contextType = AppContext;

export default Home;
