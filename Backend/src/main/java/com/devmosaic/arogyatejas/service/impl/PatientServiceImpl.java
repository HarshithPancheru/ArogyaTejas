package com.devmosaic.arogyatejas.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.devmosaic.arogyatejas.dto.DoctorAvailabilityDto;
import com.devmosaic.arogyatejas.dto.PatientAppointmentRequestDto;
import com.devmosaic.arogyatejas.dto.PatientDashboardResponseDto;
import com.devmosaic.arogyatejas.model.Appointment;
import com.devmosaic.arogyatejas.model.AppointmentStatus;
import com.devmosaic.arogyatejas.model.Doctor;
import com.devmosaic.arogyatejas.model.Patient;
import com.devmosaic.arogyatejas.model.Role;
import com.devmosaic.arogyatejas.model.User;
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
        if (authHeader == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        Optional<User> userOptional = jwtUtil.extractAllClaims(token);
        if (!userOptional.isPresent() || !userOptional.get().getRole().equals(Role.PATIENT))
            return ResponseEntity.status(401).body("Unauthorized");
        User user = userOptional.get();
        Patient patient = patientRepository.findByUser(user).getFirst();

        List<Appointment> appointments = appointmentRepository.findByPatient(patient);

        PatientDashboardResponseDto dashboard = new PatientDashboardResponseDto(patient.getUser().getFullName(),
                appointments);

        return ResponseEntity.ok(dashboard);

    }

    public ResponseEntity<?> getAvailableDoctors(String speciality) {

        List<Doctor> availableDoctors = doctorRepository.findAvailableDoctorsBySpeciality(speciality,
                LocalDateTime.now());

        List<DoctorAvailabilityDto> response = availableDoctors.stream()
                .map(d -> new DoctorAvailabilityDto(
                        d.getId(),
                        d.getUser().getFullName(),
                        d.getSpeciality(),
                        d.getExperienceYears(),
                        d.getAvailabilityEnd(),
                        d.getAvailabilityEnd()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);

    }

    public ResponseEntity<?> setAppointment(String authHeader, PatientAppointmentRequestDto dto) {
        if (authHeader == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        Optional<User> userOptional = jwtUtil.extractAllClaims(token);

        if (!userOptional.isPresent() || !userOptional.get().getRole().equals(Role.PATIENT))
            return ResponseEntity.status(401).body("Unauthorized");

        User user = userOptional.get();

        Patient patient = patientRepository.findByUser(user).getFirst();
        Appointment appointment = new Appointment();
        appointment.setStatus(AppointmentStatus.PENDING);
        Optional<Doctor> doctor = doctorRepository.findById(dto.getDoctorId());

        if (doctor.isPresent())
            appointment.setDoctor(doctor.get());
        else
            return ResponseEntity.badRequest().build();

        appointment.setPatient(patient);
        appointment.setAppointmentDateTime(dto.getDate());
        appointment.setCreatedAt(LocalDateTime.now());

        appointmentRepository.save(appointment);

        return ResponseEntity.ok(Map.of("message", "Appointment created successfully"));

    }

    public ResponseEntity<?> cancelAppointment(String authHeader, String appointmentId) {
        if (authHeader == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        Optional<User> userOptional = jwtUtil.extractAllClaims(token);

        if (!userOptional.isPresent() || !userOptional.get().getRole().equals(Role.PATIENT))
            return ResponseEntity.status(401).body("Unauthorized");

        User user = userOptional.get();
        System.out.println(appointmentId);

        Appointment appointment = appointmentRepository.getReferenceById(UUID.fromString(appointmentId));
        System.out.println("\n" + "Hey");

        if (appointment == null) {
            return ResponseEntity.status(404).body("Appointment not found");
        }

        if (!appointment.getPatient().getUser().equals(user)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        appointmentRepository.deleteByAppointmentId(appointment.getAppointmentId());

        return ResponseEntity.ok(Map.of("message", "Appointment deleted successfully"));
    }

}
