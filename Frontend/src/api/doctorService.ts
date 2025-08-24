import apiClient from './apiClient';
import type { DoctorDashboardDto } from '../types/dto';

// Fetch dashboard data for the logged-in doctor
export const getDoctorDashboard = async (): Promise<DoctorDashboardDto> => {
  try {
    const response = await apiClient.get<DoctorDashboardDto>('/doctor/dashboard');
    return response.data;
  } catch (error) {
    throw error;
  }
};

// Update the status of an appointment
export const updateDoctorAppointmentStatus = async (appointmentId: string, status: 'CONFIRMED' | 'COMPLETED' | 'CANCELLED'): Promise<void> => {
    try {
        // Assuming the endpoint is structured like this. Adjust if necessary.
        await apiClient.put(`/doctor/appointment/${appointmentId}/status`, { status });
    } catch (error) {
        throw error;
    }
};
