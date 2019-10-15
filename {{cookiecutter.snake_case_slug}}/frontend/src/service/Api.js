import { appConfig } from '../constant'

export const fetchLogin = async (email, password) => {
  try {
    const { apiKey, apiUrl } = appConfig
    const url = `${apiUrl}\login?key=${apiKey}`

    return true

  } catch (error) {
    console.log(error);
  }
};

export const fetchRegister = async (email, password, fname, lname) => {
  try {
    const { apiKey, apiUrl } = appConfig
    const url = `${apiUrl}\register?key=${apiKey}`

    return true

  } catch (error) {
    console.log(error);
  }
};