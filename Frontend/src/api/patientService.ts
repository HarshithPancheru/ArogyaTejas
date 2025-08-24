import apiClient from './apiClient';
import type {
    PatientDashboardResponseDto,
    DoctorAvailabilityDto,
    PatientAppointmentRequestDto,
    PatientAppointmentResponseDto
} from '../types/dto';

/**
 * Fetches the dashboard data for the currently logged-in patient.
 * @returns A promise that resolves to the patient's dashboard data.
 */
export const getPatientDashboard = async (): Promise<PatientDashboardResponseDto> => {
  try {
    const response = await apiClient.get<PatientDashboardResponseDto>('/patient/dashboard');
    return response.data;
  } catch (error) {
    console.error("Error fetching patient dashboard:", error);
    throw error;
  }
};

/**
 * Cancels an appointment by its ID.
 * @param appointmentId The UUID of the appointment to cancel.
 * @returns A promise that resolves when the appointment is cancelled.
 */
export const cancelAppointment = async (appointmentId: string): Promise<void> => {
    try {
        console.log(appointmentId);
        
        await apiClient.delete(`patient/appointment?appointmentId=${appointmentId}`);
    } catch (error) {
        console.error("Error cancelling appointment:", error);
        throw error;
    }
};



/**
 * Fetches a list of unique medical specialities.
 * @returns A promise that resolves to an array of speciality strings.
 */
export const getSpecialities = async (): Promise<string[]> => {
    try {
        // Assuming a new endpoint /patient/specialities exists
        const response = await apiClient.get<string[]>('/doctor/specialities');
        return response.data;
    } catch (error) {
        console.error("Error fetching specialities:", error);
        throw error;
    }
};

/**
 * Fetches a list of available doctors, optionally filtered by speciality.
 * @param speciality The medical speciality to filter by.
 * @returns A promise that resolves to an array of available doctors.
 */
export const getAvailableDoctors = async (speciality: string): Promise<DoctorAvailabilityDto[]> => {
    try {
        const response = await apiClient.get<DoctorAvailabilityDto[]>(`/patient/doctors-available?speciality=${speciality}`);
        return response.data;
    } catch (error) {
        console.error("Error fetching available doctors:", error);
        throw error;
    }
};

/**
 * Books a new appointment for a patient with a specific doctor on a given date.
 * @param requestData An object containing the doctorId and the appointment date.
 * @returns A promise that resolves to the newly created appointment's details.
 */
export const bookAppointment = async (requestData: PatientAppointmentRequestDto): Promise<PatientAppointmentResponseDto> => {
    try {
        const response = await apiClient.post<PatientAppointmentResponseDto>('/patient/appointment', requestData);
        return response.data;
    } catch (error) {
        console.error("Error booking appointment:", error);
        throw error;
    }
};