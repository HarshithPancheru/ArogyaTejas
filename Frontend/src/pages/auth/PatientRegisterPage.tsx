import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { registerPatient } from '../../api/authService';
import { toast } from 'react-toastify';
import { AxiosError } from 'axios';
import type { PatientRegistrationDto } from '../../types/dto';

const PatientRegisterPage: React.FC = () => {
  const [formData, setFormData] = useState<PatientRegistrationDto>({
    fullName: '',
    email: '',
    password: '',
    dateOfBirth: '',
    gender: '',
    phone: '',
    address: '',
  });
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleRegister = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);

    // Basic validation for password
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    if (!passwordRegex.test(formData.password)) {
      toast.error("Password must be at least 8 characters long and include uppercase, lowercase, number, and a special character.");
      setIsLoading(false);
      return;
    }

    try {
      const response = await registerPatient(formData);
      toast.success(response.message || 'Registration successful! Please log in.');
      navigate('/login');
    } catch (error) {
      const axiosError = error as AxiosError<{ message: string }>;
      const errorMessage = axiosError.response?.data?.message || 'Registration failed. Please try again.';
      toast.error(errorMessage);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="flex items-center justify-center min-h-[calc(100vh-100px)] bg-gray-50 px-4 py-12">
      <div className="w-full max-w-2xl p-8 space-y-8 bg-white rounded-2xl shadow-lg">
        <div className="text-center">
          <h2 className="text-3xl font-bold text-gray-900">Create Your Account</h2>
          <p className="mt-2 text-sm text-gray-600">Join us to manage your health appointments seamlessly.</p>
        </div>
        <form className="mt-8 space-y-6" onSubmit={handleRegister}>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <input name="fullName" type="text" required value={formData.fullName} onChange={handleChange} placeholder="Full Name" className="w-full px-4 py-3 bg-gray-100 rounded-lg focus:ring-blue-500"/>
            <input name="email" type="email" required value={formData.email} onChange={handleChange} placeholder="Email Address" className="w-full px-4 py-3 bg-gray-100 rounded-lg focus:ring-blue-500"/>
            <input name="password" type="password" required value={formData.password} onChange={handleChange} placeholder="Password" className="w-full px-4 py-3 bg-gray-100 rounded-lg focus:ring-blue-500"/>
            <input name="phone" type="tel" required value={formData.phone} onChange={handleChange} placeholder="Phone Number" className="w-full px-4 py-3 bg-gray-100 rounded-lg focus:ring-blue-500"/>
            <input name="dateOfBirth" type="date" required value={formData.dateOfBirth} onChange={handleChange} placeholder="Date of Birth" className="w-full px-4 py-3 bg-gray-100 rounded-lg focus:ring-blue-500"/>
            <select name="gender" required value={formData.gender} onChange={handleChange} className="w-full px-4 py-3 bg-gray-100 rounded-lg focus:ring-blue-500">
              <option value="" disabled>Select Gender</option>
              <option value="MALE">Male</option>
              <option value="FEMALE">Female</option>
              <option value="OTHER">Other</option>
            </select>
            <div className="md:col-span-2">
              <input name="address" type="text" required value={formData.address} onChange={handleChange} placeholder="Full Address" className="w-full px-4 py-3 bg-gray-100 rounded-lg focus:ring-blue-500"/>
            </div>
          </div>
          <div>
            <button type="submit" disabled={isLoading} className="w-full flex justify-center py-3 px-4 text-sm font-medium rounded-lg text-white bg-blue-600 hover:bg-blue-700 disabled:bg-blue-300">
              {isLoading ? 'Creating Account...' : 'Create Account'}
            </button>
          </div>
        </form>
        <div className="text-sm text-center">
          <p className="text-gray-600">
            Already have an account?{' '}
            <Link to="/login" className="font-medium text-blue-600 hover:text-blue-500">
              Sign in here
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default PatientRegisterPage;
