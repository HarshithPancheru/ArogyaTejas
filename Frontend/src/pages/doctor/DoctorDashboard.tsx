import React, { useState, useEffect } from 'react';
import { getDoctorDashboard, updateDoctorAppointmentStatus } from '../../api/doctorService';
import type { DoctorDashboardDto, DoctorAppointmentView } from '../../types/dto';
import { toast } from 'react-toastify';

const DoctorDashboard: React.FC = () => {
  const [dashboardData, setDashboardData] = useState<DoctorDashboardDto | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  const fetchDashboard = async () => {
    setIsLoading(true);
    try {
      const data = await getDoctorDashboard();
      data.appointments.sort((a, b) => new Date(a.appointmentDateTime).getTime() - new Date(b.appointmentDateTime).getTime());
      setDashboardData(data);
    } catch (error) {
      toast.error('Failed to fetch dashboard data.');
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchDashboard();
  }, []);

  const handleStatusChange = async (appointmentId: string, status: 'CONFIRMED' | 'COMPLETED' | 'CANCELLED') => {
    try {
      await updateDoctorAppointmentStatus(appointmentId, status);
      toast.success(`Appointment has been ${status.toLowerCase()}.`);
      fetchDashboard(); // Refresh data
    } catch (error) {
      toast.error('Failed to update status.');
    }
  };
  
  const getStatusBadgeColor = (status: DoctorAppointmentView['status']) => {
    switch (status) {
      case 'SCHEDULED': return 'bg-blue-100 text-blue-800';
      case 'PENDING': return 'bg-yellow-100 text-yellow-800';
      case 'COMPLETED': return 'bg-green-100 text-green-800';
      case 'CANCELLED': return 'bg-red-100 text-red-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  };

  const renderActionButtons = (appt: DoctorAppointmentView) => {
    const isPastAppointment = new Date(appt.appointmentDateTime) < new Date();
    if (isPastAppointment) {
      return null; // No actions for past appointments
    }

    switch (appt.status) {
      case 'PENDING':
        return (
          <>
            <button onClick={() => handleStatusChange(appt.appointmentId, 'CONFIRMED')} className="text-green-600 hover:text-green-800 font-semibold">
              Accept
            </button>
            <button onClick={() => handleStatusChange(appt.appointmentId, 'CANCELLED')} className="text-red-500 hover:text-red-700 font-semibold">
              Reject
            </button>
          </>
        );
      case 'SCHEDULED':
        return (
          <button onClick={() => handleStatusChange(appt.appointmentId, 'COMPLETED')} className="text-blue-600 hover:text-blue-800 font-semibold">
            Finish
          </button>
        );
      case 'COMPLETED':
      case 'CANCELLED':
      default:
        return null; // No actions for completed or cancelled appointments
    }
  };

  if (isLoading) {
    return <div className="text-center p-10">Loading Your Schedule...</div>;
  }

  return (
    <div className="container mx-auto p-4 md:p-8">
      <div className="mb-8">
        <h1 className="text-4xl font-bold text-gray-800">Dr. {dashboardData?.doctorName}'s Dashboard</h1>
        <p className="text-xl text-gray-600">{dashboardData?.specialization}</p>
      </div>
      <div className="bg-white p-6 rounded-2xl shadow-lg">
        <h2 className="text-2xl font-semibold text-gray-700 mb-6">Your Appointments</h2>
        <div className="overflow-x-auto">
          <table className="min-w-full bg-white">
            <thead className="bg-gray-50">
              <tr>
                <th className="py-3 px-4 text-left font-semibold text-gray-600">Patient</th>
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
                      <td className="py-4 px-4 font-medium">{appt.patient.user.fullName}</td>
                      <td className="py-4 px-4">{new Date(appt.appointmentDateTime).toLocaleString()}</td>
                      <td className="py-4 px-4">
                        <span className={`px-3 py-1 text-sm font-medium rounded-full ${getStatusBadgeColor(appt.status)}`}>
                          {appt.status}
                        </span>
                      </td>
                      <td className="py-4 px-4 space-x-4">
                        {renderActionButtons(appt)}
                      </td>
                    </tr>
                  );
                })
              ) : (
                <tr>
                  <td colSpan={4} className="text-center py-10 text-gray-500">
                    You have no scheduled appointments.
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

export default DoctorDashboard;
