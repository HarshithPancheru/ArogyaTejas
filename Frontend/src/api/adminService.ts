import apiClient from './apiClient';
import type { AdminDashboardDto, DoctorRegistrationDto, AuthResponseDto } from '../types/dto';

// Fetch all appointment data for the admin dashboard
export const getAdminDashboard = async (): Promise<AdminDashboardDto> => {
  try {
    const response = await apiClient.get<AdminDashboardDto>('/admin/dashboard');
    return response.data;
  } catch (error) {
    throw error;
  }
};

// Register a new doctor
export const registerDoctor = async (doctorData: DoctorRegistrationDto): Promise<AuthResponseDto> => {
    try {
        // The endpoint for admin to create a doctor is likely different from a public one
        const response = await apiClient.post<AuthResponseDto>('/auth/doctor/register', doctorData);
        return response.data;
    } catch (error) {
        throw error;
    }
};

// Update the status of an appointment
export const updateAppointmentStatus = async (appointmentId: string, status: 'SCHEDULED' | 'COMPLETED' | 'CANCELLED'): Promise<void> => {
    try {
        await apiClient.put(`/admin/appointment/${appointmentId}/status`, { status });
    } catch (error) {
        throw error;
    }
};

// Delete an appointment
export const deleteAppointment = async (appointmentId: string): Promise<void> => {
    try {
        await apiClient.delete(`/admin/appointment/${appointmentId}`);
    } catch (error) {
        throw error;
    }
};
