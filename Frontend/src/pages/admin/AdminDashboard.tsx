import React, { useState, useEffect, useMemo } from 'react';
import { Link } from 'react-router-dom';
import { getAdminDashboard, deleteAppointment, updateAppointmentStatus } from '../../api/adminService';
import type { AdminDashboardDto, AdminAppointmentView } from '../../types/dto';
import { toast } from 'react-toastify';

const AdminDashboard: React.FC = () => {
  const [dashboardData, setDashboardData] = useState<AdminDashboardDto | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [filter, setFilter] = useState('');

  const fetchDashboard = async () => {
    setIsLoading(true);
    try {
      const data = await getAdminDashboard();
      setDashboardData(data);
    } catch (error) {
      toast.error('Failed to fetch admin dashboard data.');
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchDashboard();
  }, []);
  
  const handleDelete = async (id: string) => {
    if (window.confirm('Are you sure you want to delete this appointment?')) {
      try {
        await deleteAppointment(id);
        toast.success('Appointment deleted.');
        fetchDashboard(); // Refresh data
      } catch (error) {
        toast.error('Failed to delete appointment.');
      }
    }
  };

  const handleStatusChange = async (id: string, status: AdminAppointmentView['status']) => {
     try {
        await updateAppointmentStatus(id, status);
        toast.success('Status updated.');
        fetchDashboard(); // Refresh data
      } catch (error) {
        toast.error('Failed to update status.');
      }
  };

  const filteredAppointments = useMemo(() => {
    if (!dashboardData) return [];
    return dashboardData.appointments.filter(
      (appt) =>
        appt.patient.fullName.toLowerCase().includes(filter.toLowerCase()) ||
        appt.doctor.fullName.toLowerCase().includes(filter.toLowerCase())
    );
  }, [dashboardData, filter]);

  if (isLoading) return <div className="text-center p-10">Loading Admin Dashboard...</div>;

  return (
    <div className="container mx-auto p-4 md:p-8">
        <div className="flex justify-between items-center mb-8">
            <h1 className="text-4xl font-bold text-gray-800">Admin Dashboard</h1>
            <Link to="/admin/create-doctor" className="bg-green-600 text-white font-bold py-2 px-6 rounded-lg hover:bg-green-700 transition duration-300">
            + Add New Doctor
            </Link>
        </div>

        {/* Stats Cards */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
            <div className="bg-blue-500 text-white p-6 rounded-2xl shadow-lg">
                <h3 className="text-xl font-semibold">Total Appointments</h3>
                <p className="text-4xl font-bold">{dashboardData?.totalAppointments}</p>
            </div>
            <div className="bg-yellow-500 text-white p-6 rounded-2xl shadow-lg">
                <h3 className="text-xl font-semibold">Scheduled</h3>
                <p className="text-4xl font-bold">{dashboardData?.scheduledAppointments}</p>
            </div>
            <div className="bg-green-500 text-white p-6 rounded-2xl shadow-lg">
                <h3 className="text-xl font-semibold">Completed</h3>
                <p className="text-4xl font-bold">{dashboardData?.completedAppointments}</p>
            </div>
        </div>

        {/* Appointments Table */}
        <div className="bg-white p-6 rounded-2xl shadow-lg">
            <h2 className="text-2xl font-semibold text-gray-700 mb-4">Manage Appointments</h2>
            <input type="text" placeholder="Search by patient or doctor..." value={filter} onChange={(e) => setFilter(e.target.value)} className="w-full p-2 mb-4 border rounded-lg"/>
            <div className="overflow-x-auto">
                <table className="min-w-full bg-white">
                    <thead className="bg-gray-100">
                        <tr>
                            <th className="py-3 px-4 text-left">Patient</th>
                            <th className="py-3 px-4 text-left">Doctor</th>
                            <th className="py-3 px-4 text-left">Date & Time</th>
                            <th className="py-3 px-4 text-left">Status</th>
                            <th className="py-3 px-4 text-left">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {filteredAppointments.map((appt) => (
                        <tr key={appt.id} className="border-b hover:bg-gray-50">
                            <td className="py-3 px-4">{appt.patient.fullName}</td>
                            <td className="py-3 px-4">{appt.doctor.fullName}</td>
                            <td className="py-3 px-4">{new Date(appt.appointmentDate).toLocaleString()}</td>
                            <td className="py-3 px-4">
                                <select value={appt.status} onChange={(e) => handleStatusChange(appt.id, e.target.value as AdminAppointmentView['status'])} className="p-1 border rounded-md">
                                    <option value="SCHEDULED">Scheduled</option>
                                    <option value="COMPLETED">Completed</option>
                                    <option value="CANCELLED">Cancelled</option>
                                </select>
                            </td>
                            <td className="py-3 px-4">
                                <button onClick={() => handleDelete(appt.id)} className="text-red-500 hover:text-red-700 font-semibold">Delete</button>
                            </td>
                        </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    </div>
  );
};

export default AdminDashboard;
