import { appConfig } from '../constant'

export const fetchLogin = async (email, password) => {
  try {
    const { apiKey, apiUrl } = appConfig
    const url = `${apiUrl}/login` //no apiKey for now

    const data = {
      "emailAddress": email,
      "password" : password,
    }

    // Default options are marked with *
    const response = await fetch(url, {
      method: 'POST', 
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data), 
    });

    const json = response.json(); 

    return true

  } catch (error) {
    console.log(error);
  }
};

export const fetchRegister = async (email, password, fname, lname) => {
  try {
    const { apiKey, apiUrl } = appConfig
    const url = `${apiUrl}user/registration` //no apiKey for now

    const data = {
      "firstName" : fname,
      "lastName" : lname,
      "password" : password,
      "matchingPassword" : password,
      "emailAddress": email,
    }

    // Default options are marked with *
    const response = await fetch(url, {
      method: 'POST', 
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data), 
    });

    const json = response.json(); 

    return true

  } catch (error) {
    console.log(error);
  }
};