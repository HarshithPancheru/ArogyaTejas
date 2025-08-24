import React, { useContext } from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { AuthContext } from '../contexts/AuthContext';
import type { Role } from './roles';

interface ProtectedRouteProps {
  allowedRoles: Role[];
}

const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ allowedRoles }) => {
  const authContext = useContext(AuthContext);

  if (!authContext) {
    // This should ideally not happen if the component is used within AuthProvider
    return <Navigate to="/login" replace />;
  }

  const { isAuthenticated, role } = authContext;

  if (!isAuthenticated) {
    // If user is not authenticated, redirect to login page
    return <Navigate to="/login" replace />;
  }

  if (role && !allowedRoles.includes(role)) {
    // If user is authenticated but does not have an allowed role,
    // redirect them to a default page or a 'not authorized' page.
    // For simplicity, we'll redirect to the home page.
    return <Navigate to="/" replace />;
  }

  // If authenticated and has the correct role, render the child components
  return <Outlet />;
};

export default ProtectedRoute;
