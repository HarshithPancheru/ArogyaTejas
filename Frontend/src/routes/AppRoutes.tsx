import React from 'react';
import { Routes, Route } from 'react-router-dom';
import { ROLES } from './roles';

// Import Page Components
import HomePage from '../pages/HopePage';
import LoginPage from '../pages/auth/LoginPage';
import PatientRegisterPage from '../pages/auth/PatientRegisterPage';
import AdminDashboard from '../pages/admin/AdminDashboard';
import CreateDoctor from '../pages/admin/CreateDoctor';
import DoctorDashboard from '../pages/doctor/DoctorDashboard';
import PatientDashboard from '../pages/patient/PatientDashboard';
import BookAppointment from '../pages/patient/BookAppointment';
import NotFoundPage from '../pages/NotFoundPage';
import ProtectedRoute from './ProtectedRoute';
import Navbar from '../components/common/Navbar'; // Assuming a Navbar component

const AppRoutes: React.FC = () => {
  return (
    <>
      <Navbar />
      <main className="container mx-auto mt-8 p-4">
        <Routes>
          {/* Public Routes */}
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<PatientRegisterPage />} />

          {/* Protected Admin Routes */}
          <Route element={<ProtectedRoute allowedRoles={[ROLES.ADMIN]} />}>
            <Route path="/admin/dashboard" element={<AdminDashboard />} />
            <Route path="/admin/create-doctor" element={<CreateDoctor />} />
          </Route>

          {/* Protected Doctor Routes */}
          <Route element={<ProtectedRoute allowedRoles={[ROLES.DOCTOR]} />}>
            <Route path="/doctor/dashboard" element={<DoctorDashboard />} />
          </Route>

          {/* Protected Patient Routes */}
          <Route element={<ProtectedRoute allowedRoles={[ROLES.PATIENT]} />}>
            <Route path="/patient/dashboard" element={<PatientDashboard />} />
            <Route path="/patient/book-appointment" element={<BookAppointment />} />
          </Route>

          {/* Catch-all Route for 404 Not Found */}
          <Route path="*" element={<NotFoundPage />} />
        </Routes>
      </main>
    </>
  );
};

export default AppRoutes;
