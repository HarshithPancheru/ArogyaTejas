import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getSpecialities, getAvailableDoctors, bookAppointment } from '../../api/patientService';
import type { DoctorAvailabilityDto } from '../../types/dto';
import { toast } from 'react-toastify';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

const BookAppointment: React.FC = () => {
  const [step, setStep] = useState(1); // 1: Select Speciality, 2: Select Doctor & Date
  const [specialities, setSpecialities] = useState<string[]>([]);
  const [doctors, setDoctors] = useState<DoctorAvailabilityDto[]>([]);
  const [selectedSpeciality, setSelectedSpeciality] = useState<string | null>(null);
  const [selectedDoctor, setSelectedDoctor] = useState<DoctorAvailabilityDto | null>(null);
  const [selectedDate, setSelectedDate] = useState<Date | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [isBooking, setIsBooking] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchSpecialities = async () => {
      try {
        const data = await getSpecialities();
        setSpecialities(data);
      } catch (error) {
        toast.error('Failed to fetch specialities.');
      } finally {
        setIsLoading(false);
      }
    };
    fetchSpecialities();
  }, []);

  const handleSelectSpeciality = async (speciality: string) => {
    setSelectedSpeciality(speciality);
    setIsLoading(true);
    setStep(2);
    try {
      const data = await getAvailableDoctors(speciality);
      setDoctors(data);
      console.log(data);
      console.log(doctors);
      
    } catch (error) {
      toast.error(`Failed to fetch doctors for ${speciality}.`);
    } finally {
      setIsLoading(false);
    }
  };

  const handleBooking = async () => {
    if (!selectedDoctor || !selectedDate) {
      toast.warn('Please select a doctor and a date.');
      return;
    }
    setIsBooking(true);
    try {
      console.log("selece"+selectedDoctor);
      
      await bookAppointment({ date: selectedDate , doctorId: selectedDoctor.id,});
      toast.success('Appointment booked successfully!');
      navigate('/patient/dashboard');
    } catch (error) {
      console.log(error);
      
      toast.error('Failed to book appointment. The slot might be unavailable.');
    } finally {
      setIsBooking(false);
    }
  };

  const resetSelection = () => {
    setStep(1);
    setSelectedSpeciality(null);
    setSelectedDoctor(null);
    setDoctors([]);
    setSelectedDate(null);
  };

  return (
    <div className="container mx-auto p-4 md:p-8">
      <h1 className="text-4xl font-bold text-gray-800 mb-8">Book an Appointment</h1>

      {/* Step 1: Select Speciality */}
      {step === 1 && (
        <div>
          <h2 className="text-2xl font-semibold mb-4">1. Select a Speciality</h2>
          {isLoading ? <p>Loading specialities...</p> : (
            <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
              {specialities.map(spec => (
                <button key={spec} onClick={() => handleSelectSpeciality(spec)} className="p-6 bg-white rounded-lg shadow-md hover:shadow-xl hover:bg-blue-500 hover:text-white transition-all text-lg font-semibold">
                  {spec}
                </button>
              ))}
            </div>
          )}
        </div>
      )}

      {/* Step 2: Select Doctor and Date */}
      {(step === 2 || step==3)&& selectedSpeciality && (
        <div>
          <div className="flex justify-between items-center mb-4">
            <h2 className="text-2xl font-semibold">2. Select a Doctor for <span className="text-blue-600">{selectedSpeciality}</span></h2>
            <button onClick={resetSelection} className="text-sm text-blue-600 hover:underline">Change Speciality</button>
          </div>
          {isLoading ? <p>Finding doctors...</p> : (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
              {doctors.map(doc => (
                <div key={doc.id} className={`p-5 rounded-lg shadow-md border-2 transition-all ${selectedDoctor?.id === doc.id ? 'border-blue-500 bg-blue-50' : 'border-transparent bg-white'}`}>
                  <h3 className="text-xl font-bold">{doc.fullName}</h3>
                  <p className="text-gray-600">{doc.experience} years of experience</p>
                  <button onClick={() => { setSelectedDoctor(doc); setSelectedDate(null); setStep(3)}} className="mt-4 w-full bg-blue-100 text-blue-700 font-semibold py-2 rounded-lg hover:bg-blue-200">
                    Select Dr. {doc.fullName.split(' ').pop()}
                  </button>
                </div>
              ))}
            </div>
          )}
        </div>
      )}



      {/* Date Picker appears after doctor is selected */}
      {step == 3 && selectedDoctor && (
        <div className="mt-4">
          <label className="block font-semibold mb-2">3. Pick a Date</label>
          <DatePicker
            selected={selectedDate}
            onChange={(date) => setSelectedDate(date)}
            minDate={new Date()}
            maxDate={new Date(selectedDoctor.nextAvailabilityEnd)}
            dateFormat="MMMM d, yyyy"
            className="w-full p-2 border border-gray-300 rounded-lg"
            inline
          />
          <button onClick={handleBooking} disabled={!selectedDate || isBooking} className="mt-4 w-full bg-blue-600 text-white font-bold py-3 rounded-lg hover:bg-blue-700 disabled:bg-gray-400">
            {isBooking ? 'Booking...' : 'Confirm Appointment'}
          </button>
        </div>
      )}
    </div>
  );
};

export default BookAppointment;
