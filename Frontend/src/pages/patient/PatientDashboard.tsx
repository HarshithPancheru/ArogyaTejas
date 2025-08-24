import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { getPatientDashboard, cancelAppointment } from '../../api/patientService';
import type { PatientDashboardResponseDto, Appointment } from '../../types/dto';
import { toast } from 'react-toastify';

const PatientDashboard: React.FC = () => {
  const [dashboardData, setDashboardData] = useState<PatientDashboardResponseDto | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  const fetchDashboard = async () => {
    try {
      const data = await getPatientDashboard();
      // Sort appointments by date, newest first
      data.appointments.sort((a, b) => new Date(b.appointmentDateTime).getTime() - new Date(a.appointmentDateTime).getTime());
      setDashboardData(data);
      console.log(data);
      
    } catch (error) {
      toast.error('Failed to fetch dashboard data.');
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchDashboard();
  }, []);

  const handleCancelAppointment = async (appointmentId: string) => {
    if (window.confirm('Are you sure you want to cancel this appointment?')) {
        try {
          
            await cancelAppointment(appointmentId);
            toast.success('Appointment cancelled successfully.');
            fetchDashboard(); // Refresh the dashboard data
        } catch (error) {
            toast.error('Failed to cancel appointment.');
        }
    }
  };

  const getStatusBadgeColor = (status: Appointment['status']) => {
    switch (status) {
      case 'SCHEDULED': return 'bg-blue-100 text-blue-800';
      case 'COMPLETED': return 'bg-green-100 text-green-800';
      case 'CANCELLED': return 'bg-red-100 text-red-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  };

  if (isLoading) {
    return <div className="text-center p-10">Loading dashboard...</div>;
  }

  return (
    <div className="container mx-auto p-4 md:p-8">
      <div className="flex justify-between items-center mb-8">
        <h1 className="text-4xl font-bold text-gray-800">
          Welcome, {dashboardData?.fullName}!
        </h1>
        <Link to="/patient/book-appointment" className="bg-blue-600 text-white font-bold py-2 px-6 rounded-lg hover:bg-blue-700 transition duration-300">
          + Book New Appointment
        </Link>
      </div>

      <div className="bg-white p-6 rounded-2xl shadow-lg">
        <h2 className="text-2xl font-semibold text-gray-700 mb-6">Your Appointments</h2>
        <div className="overflow-x-auto">
          <table className="min-w-full bg-white">
            <thead className="bg-gray-50">
              <tr>
                <th className="py-3 px-4 text-left font-semibold text-gray-600">Doctor</th>
                <th className="py-3 px-4 text-left font-semibold text-gray-600">Speciality</th>
                <th className="py-3 px-4 text-left font-semibold text-gray-600">Date & Time</th>
                <th className="py-3 px-4 text-left font-semibold text-gray-600">Status</th>
                <th className="py-3 px-4 text-left font-semibold text-gray-600">Actions</th>
              </tr>
            </thead>
            <tbody>
              {dashboardData?.appointments && dashboardData.appointments.length > 0 ? (
                dashboardData.appointments.map((appt) => {
                  const isPastAppointment = new Date(appt.appointmentDateTime) < new Date();
                  return (
                    <tr key={appt.appointmentId} className={`border-b ${isPastAppointment ? 'bg-gray-100 text-gray-500' : ''}`}>
                      <td className="py-4 px-4">
                        <div className="font-medium">{appt.doctor.user.fullName}</div>
                        <div className="text-xs text-gray-400">ID: {appt.doctor.id}</div>
                      </td>
                      <td className="py-4 px-4">{appt.doctor.speciality}</td>
                      <td className="py-4 px-4">{new Date(appt.appointmentDateTime).toLocaleString()}</td>
                      <td className="py-4 px-4">
                        <span className={`px-3 py-1 text-sm font-medium rounded-full ${getStatusBadgeColor(appt.status)}`}>
                          {appt.status}
                        </span>
                      </td>
                      <td className="py-4 px-4">
                        {/* Show cancel button only if appointment is in the future and scheduled */}
                        {!isPastAppointment && appt.status=="PENDING"&& (
                          <button onClick={() => handleCancelAppointment(appt.appointmentId)} className="text-red-500 hover:text-red-700 font-semibold">
                            Cancel
                          </button>
                        )}
                      </td>
                    </tr>
                  );
                })
              ) : (
                <tr>
                  <td colSpan={5} className="text-center py-10 text-gray-500">
                    You have no appointments.
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default PatientDashboard;
