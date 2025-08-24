import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { registerDoctor } from '../../api/adminService';
import type { DoctorRegistrationDto } from '../../types/dto';
import { toast } from 'react-toastify';
import { AxiosError } from 'axios';

const CreateDoctor: React.FC = () => {
  const [formData, setFormData] = useState<DoctorRegistrationDto>({
    fullName: '',
    email: '',
    password: '',
    specialization: '',
    licenseNumber: '',
    experienceYears: 0,
    availabilityStart: '',
    availabilityEnd: '',
  });
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value, type } = e.target;
    setFormData({
      ...formData,
      [name]: type === 'number' ? parseInt(value, 10) || 0 : value,
    });
  };

  const handleDateTimeChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    // Converts local datetime-local input to ISO 8601 format
    const localDate = new Date(e.target.value);
    const isoString = localDate.toISOString();
    setFormData({ ...formData, [e.target.name]: isoString });
  };


  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);

    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    if (!passwordRegex.test(formData.password)) {
      toast.error("Password must be at least 8 characters and include uppercase, lowercase, number, and special character.");
      setIsLoading(false);
      return;
    }
    
    if (!formData.availabilityStart || !formData.availabilityEnd) {
        toast.error("Please set both availability start and end times.");
        setIsLoading(false);
        return;
    }

    try {
      await registerDoctor(formData);
      toast.success('Doctor account created successfully!');
      navigate('/admin/dashboard');
    } catch (error) {
      const axiosError = error as AxiosError<{ message: string }>;
      const errorMessage = axiosError.response?.data?.message || 'Failed to create doctor account.';
      toast.error(errorMessage);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="container mx-auto p-4 md:p-8 max-w-3xl">
      <div className="bg-white p-8 rounded-2xl shadow-lg">
        <h1 className="text-3xl font-bold text-gray-800 mb-2">Create New Doctor</h1>
        <p className="text-gray-600 mb-8">Fill out the form to add a new doctor to the system.</p>
        
        <form onSubmit={handleSubmit} className="space-y-6">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <input name="fullName" type="text" value={formData.fullName} onChange={handleChange} placeholder="Full Name" required className="w-full p-3 bg-gray-50 rounded-lg border focus:ring-blue-500"/>
            <input name="email" type="email" value={formData.email} onChange={handleChange} placeholder="Email Address" required className="w-full p-3 bg-gray-50 rounded-lg border focus:ring-blue-500"/>
            <input name="password" type="password" value={formData.password} onChange={handleChange} placeholder="Password" required className="w-full p-3 bg-gray-50 rounded-lg border focus:ring-blue-500"/>
            <input name="specialization" type="text" value={formData.specialization} onChange={handleChange} placeholder="Specialization" required className="w-full p-3 bg-gray-50 rounded-lg border focus:ring-blue-500"/>
            <input name="licenseNumber" type="text" value={formData.licenseNumber} onChange={handleChange} placeholder="License Number" required className="w-full p-3 bg-gray-50 rounded-lg border focus:ring-blue-500"/>
            <input name="experienceYears" type="number" value={formData.experienceYears} onChange={handleChange} placeholder="Years of Experience" required className="w-full p-3 bg-gray-50 rounded-lg border focus:ring-blue-500"/>
          </div>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Availability Start</label>
                <input name="availabilityStart" type="datetime-local" onChange={handleDateTimeChange} required className="w-full p-3 bg-gray-50 rounded-lg border focus:ring-blue-500"/>
            </div>
            <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Availability End</label>
                <input name="availabilityEnd" type="datetime-local" onChange={handleDateTimeChange} required className="w-full p-3 bg-gray-50 rounded-lg border focus:ring-blue-500"/>
            </div>
          </div>
          <div className="flex justify-end pt-4">
            <button type="submit" disabled={isLoading} className="bg-green-600 text-white font-bold py-3 px-8 rounded-lg hover:bg-green-700 transition duration-300 disabled:bg-green-300">
              {isLoading ? 'Creating...' : 'Create Doctor'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default CreateDoctor;
