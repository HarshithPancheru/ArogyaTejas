import apiClient from './apiClient';
import type { LoginDto, PatientRegistrationDto, AuthResponseDto } from '../types/dto'; // Assuming you'll create a types folder
 // Assuming you'll create a types folder

// Function to handle user login
export const loginUser = async (credentials: LoginDto): Promise<AuthResponseDto> => {
  try {
    const response = await apiClient.post<AuthResponseDto>('/auth/login', credentials);
    return response.data;
  } catch (error) {
    // The error will be handled by the component that calls this function
    throw error;
  }
};

// Function to handle patient registration
export const registerPatient = async (patientData: PatientRegistrationDto): Promise<AuthResponseDto> => {
  try {
    const response = await apiClient.post<AuthResponseDto>('/auth/patient/register', patientData);
    return response.data;
  } catch (error) {
    throw error;
  }
};

// You can add other auth-related API calls here, like admin or doctor registration if needed from the client.
