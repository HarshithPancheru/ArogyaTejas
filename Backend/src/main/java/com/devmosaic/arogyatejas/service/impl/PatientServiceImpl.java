package com.devmosaic.arogyatejas.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.devmosaic.arogyatejas.dto.DoctorAvailabilityDto;
import com.devmosaic.arogyatejas.dto.PatientAppointmentRequestDto;
import com.devmosaic.arogyatejas.dto.PatientDashboardResponseDto;
import com.devmosaic.arogyatejas.exception.DoctorNotFoundException;
import com.devmosaic.arogyatejas.exception.PatientNotFoundException;
import com.devmosaic.arogyatejas.model.Appointment;
import com.devmosaic.arogyatejas.model.Doctor;
import com.devmosaic.arogyatejas.model.Patient;
import com.devmosaic.arogyatejas.repository.AppointmentRepository;
import com.devmosaic.arogyatejas.repository.DoctorRepository;
import com.devmosaic.arogyatejas.repository.PatientRepository;
import com.devmosaic.arogyatejas.security.JwtUtil;
import com.devmosaic.arogyatejas.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<?> loadDashboard(String authHeader) {

        try {
            if(authHeader==null) return ResponseEntity.status(401).body("Unauthorized");
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        Map<String, Object> claims = jwtUtil.extractAllClaims(token);

        // Get userId from claims
        Long userId = Long.parseLong(claims.get("userId").toString());

            Optional<Patient> patientOpt = this.patientRepository.findById(userId);
            Patient patient = (Patient) patientOpt.get();

            List<Appointment> appointments = appointmentRepository.findByPatient(patient);

            PatientDashboardResponseDto dashboard = new PatientDashboardResponseDto(patient.getFirstName(),
                    patient.getLastName(), appointments);

            return ResponseEntity.ok(dashboard);

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
    }

    public ResponseEntity<?> getAvailableDoctors(String authHeader) {
        try {
            List<Doctor> availableDoctors = doctorRepository.findAll();

            List<DoctorAvailabilityDto> response = availableDoctors.stream()
                    .map(d -> new DoctorAvailabilityDto(
                            d.getFirstName(),
                            d.getLastName(),
                            d.getSpeciality(),
                            d.getExperience(),
                            d.getNextAvailability()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
    }

    public ResponseEntity<?> setAppointment(String authHeader, PatientAppointmentRequestDto dto) {
        try {
            if(authHeader==null) return ResponseEntity.status(401).body("Unauthorized");
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        Map<String, Object> claims = jwtUtil.extractAllClaims(token);

        // Get userId from claims
        Long userId = Long.parseLong(claims.get("userId").toString());

            Appointment appointment = new Appointment();
            appointment.setStatus("pending");
            Optional<Doctor> doctor = doctorRepository.findById(dto.getDoctorId());
            if (doctor.isPresent())
                appointment.setDoctor(doctor.get());
            else
                throw new DoctorNotFoundException();

                Optional<Patient> patient = patientRepository.findById(userId);
                if (patient.isPresent())
                    appointment.setPatient(patient.get());
                else
                    throw new PatientNotFoundException();
                    appointmentRepository.save(appointment);

            return ResponseEntity.ok(Map.of("message","Appointment created successfully"));

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error");
        }
    }
}
