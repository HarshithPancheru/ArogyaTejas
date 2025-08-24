import React, { useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { AuthContext } from '../../contexts/AuthContext';
// import logo from '../../../public/logo.png';

const Navbar: React.FC = () => {
  const authContext = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    if (authContext) {
      authContext.logout();
      navigate('/login');
    }
  };

  const renderNavLinks = () => {
    if (!authContext || !authContext.isAuthenticated) {
      return (
        <>
          <Link to="/login" className="text-gray-700 hover:text-blue-600 font-medium">Login</Link>
          <Link to="/register" className="bg-blue-600 text-white px-4 py-2 rounded-lg font-medium hover:bg-blue-700">Register</Link>
        </>
      );
    }

    let dashboardPath = '/';
    if (authContext.role === 'ADMIN') dashboardPath = '/admin/dashboard';
    if (authContext.role === 'DOCTOR') dashboardPath = '/doctor/dashboard';
    if (authContext.role === 'PATIENT') dashboardPath = '/patient/dashboard';

    return (
      <>
        <Link to={dashboardPath} className="text-gray-700 hover:text-blue-600 font-medium">Dashboard</Link>
        <button onClick={handleLogout} className="bg-red-500 text-white px-4 py-2 rounded-lg font-medium hover:bg-red-600">
          Logout
        </button>
      </>
    );
  };

  return (
    <nav className="bg-white shadow-md sticky top-0 z-50">
      <div className="container mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between h-16">
          <div className="flex-shrink-0">
            <Link to="/" className="text-2xl font-bold text-blue-600">
              ArogyaTejas
            </Link>
          </div>
          <div className="flex items-center space-x-4">
            {renderNavLinks()}
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
