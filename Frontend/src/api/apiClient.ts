import axios from 'axios';

// Create an Axios instance with a base URL from environment variables.
// This makes it easy to switch between development and production environments.
const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
});

// Add a request interceptor to include the JWT token in the Authorization header
// for every outgoing request if the token exists in local storage.
apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default apiClient;
