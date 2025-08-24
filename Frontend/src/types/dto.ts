// --- Authentication ---

export interface LoginDto {
  email: string;
  password: string;
}

export interface AuthResponseDto {
  token: string;
  role: 'ADMIN' | 'PATIENT' | 'DOCTOR';
  message: string;
}

// --- Registration ---

export interface PatientRegistrationDto {
  fullName: string;
  password: string;
  email: string;
  dateOfBirth: string; // "YYYY-MM-DD"
  gender: string;
  phone: string;
  address: string;
}

export interface DoctorRegistrationDto {
  fullName: string;
  password: string;
  email: string;
  specialization: string;
  licenseNumber: string;
  experienceYears: number;
  availabilityStart: string; // ISO 8601 format
  availabilityEnd: string;   // ISO 8601 format
}

// --- Appointments ---

// Corrected Appointment type based on your PatientDashboard code
export interface Appointment {
  appointmentId: string;
  appointmentDateTime: string; // ISO 8601 format
  status: 'SCHEDULED' | 'COMPLETED' | 'CANCELLED' | 'PENDING';
  doctor: {
    id: string; // Doctor's UUID
    speciality: string;
    user: {
        fullName: string;
    }
  };
}

export interface PatientAppointmentRequestDto {
  date: Date; // "YYYY-MM-DD"
  doctorId: string; // UUID
}

export interface PatientAppointmentResponseDto {
  id: number;
  time: string;
  date: string;
  status: string;
}

// --- Patient Dashboard & Data ---

export interface PatientDashboardResponseDto {
  fullName: string;
  appointments: Appointment[];
}

export interface DoctorAvailabilityDto {
  id: string;
  fullName: string;
  speciality: string;
  experience: number;
  nextAvailabilityStart: string;
  nextAvailabilityEnd: string;
}

// --- Doctor Dashboard ---

// Corrected Doctor's view of an appointment
export interface DoctorAppointmentView {
  appointmentId: string;
  appointmentDateTime: string;
  status: 'SCHEDULED' | 'COMPLETED' | 'CANCELLED' | 'PENDING';
  patient: {
      user: {
          fullName: string;
      },
      phone: string;
  };
}

export interface DoctorDashboardDto {
  doctorName: string;
  specialization: string;
  appointments: DoctorAppointmentView[];
}

// --- Admin Dashboard ---

export interface AdminAppointmentView {
  id: string;
  appointmentDate: string;
  status: 'SCHEDULED' | 'COMPLETED' | 'CANCELLED' | 'PENDING';
  patient: {
    fullName: string;
    email: string;
  };
  doctor: {
    fullName: string;
    specialization: string;
  };
}

export interface AdminDashboardDto {
  totalAppointments: number;
  scheduledAppointments: number;
  completedAppointments: number;
  appointments: AdminAppointmentView[];
}
