import { appConfig } from "../constant";

export const fetchLogin = async (email, password) => {
  try {
    const { apiKey, apiUrl } = appConfig;
    const url = `${apiUrl}/login`; //no apiKey for now

    const data = {
      username: email,
      password: password
    };

    // Default options are marked with *
    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(data)
    });

    if (response.status != 200)
      return {
        status: false,
        message: "Login failed."
      };

    return {
      status: true,
      token: response.headers.get("Authorization")
    };
  } catch (error) {
    console.log(error);
    return {
      status: false,
      message: "Login failed."
    };
  }
};

export const fetchRegister = async (email, password, fname, lname) => {
  try {
    const { apiKey, apiUrl } = appConfig;
    const url = `${apiUrl}/users/sign-up`; //no apiKey for now

    const data = {
      firstName: fname,
      lastName: lname,
      password: password,
      matchingPassword: password,
      username: email
    };

    // Default options are marked with *
    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(data)
    });

    if (response.status != 200)
      return {
        status: false,
        message: "Register failed."
      };

    return {
      status: true,
      message: "Register user successfully."
    };
  } catch (error) {
    console.log(error);
    return {
      status: false,
      message: "Register failed. " + error
    };
  }
};

export const fetchCreateTask = async (token, description) => {
  try {
    const { apiKey, apiUrl } = appConfig;
    const url = `${apiUrl}/tasks`; //no apiKey for now

    const data = {
      description: description
    };

    // Default options are marked with *
    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token
      },
      body: JSON.stringify(data)
    });

    const json = await response.json();

    return {
      status: true,
      message: "Create task successfully"
    };
  } catch (error) {
    console.log(error);
    return {
      status: false,
      message: error
    };
  }
};

export const fetchGetTasks = async token => {
  try {
    const { apiKey, apiUrl } = appConfig;
    const url = `${apiUrl}/tasks`; //no apiKey for now

    // Default options are marked with *
    const response = await fetch(url, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token
      }
    });

    const json = await response.json();

    return {
      status: true,
      data: json
    };
  } catch (error) {
    console.log(error);
    return {
      status: false,
      data: []
    };
  }
};

export const fetchUpdateTasks = async (token, id, description) => {
  try {
    const { apiKey, apiUrl } = appConfig;
    const url = `${apiUrl}/tasks/${id}`; //no apiKey for now

    const data = {
      description: description
    };

    // Default options are marked with *
    const response = await fetch(url, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token
      },
      body: JSON.stringify(data)
    });

    const json = await response.json();

    return {
      status: true,
      data: json
    };
  } catch (error) {
    console.log(error);
    return {
      status: false,
      data: {}
    };
  }
};

export const fetchDeleteTask = async (token, id) => {
  try {
    const { apiKey, apiUrl } = appConfig;
    const url = `${apiUrl}/tasks/${id}`; //no apiKey for now

    // Default options are marked with *
    const response = await fetch(url, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token
      },
      body: {}
    });

    const json = await response.json();

    return {
      status: true,
      data: json
    };
  } catch (error) {
    console.log(error);
    return {
      status: false,
      data: {}
    };
  }
};
