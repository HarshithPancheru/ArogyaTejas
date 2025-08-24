package com.devmosaic.arogyatejas.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.devmosaic.arogyatejas.dto.PatientDashboardResponseDto;
import com.devmosaic.arogyatejas.model.Appointment;
import com.devmosaic.arogyatejas.model.AppointmentStatus;
import com.devmosaic.arogyatejas.model.Doctor;
import com.devmosaic.arogyatejas.model.Role;
import com.devmosaic.arogyatejas.model.User;
import com.devmosaic.arogyatejas.repository.AppointmentRepository;
import com.devmosaic.arogyatejas.repository.DoctorRepository;
import com.devmosaic.arogyatejas.security.JwtUtil;
import com.devmosaic.arogyatejas.service.*;

import jakarta.transaction.Transactional;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public ResponseEntity<?> loadDashboard(String authHeader) {
        if (authHeader == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        Optional<User> userOptional = jwtUtil.extractAllClaims(token);
        if (!userOptional.isPresent() || !userOptional.get().getRole().equals(Role.DOCTOR))
            return ResponseEntity.status(401).body("Unauthorized");
        
        User user = userOptional.get();
        Doctor doctor = doctorRepository.findByUser(user).getFirst();

        List<Appointment> appointments = appointmentRepository.findByDoctor(doctor);

        PatientDashboardResponseDto dashboard = new PatientDashboardResponseDto(doctor.getUser().getFullName(),
                appointments);

        return ResponseEntity.ok(dashboard);
    }

    @Transactional
    @Override
    public ResponseEntity<?> setStatus(String authHeader, UUID appointmentId) {
        if (authHeader == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        Optional<User> userOptional = jwtUtil.extractAllClaims(token);

        if (!userOptional.isPresent() || !userOptional.get().getRole().equals(Role.DOCTOR))
            return ResponseEntity.status(401).body("Unauthorized");

        User user = userOptional.get();

        Doctor doctor = doctorRepository.findByUser(user).getFirst();

        int result = appointmentRepository.updateStatusAndTime(doctor.getId(), appointmentId, AppointmentStatus.CANCELED,
                LocalDateTime.now());

        if (result > 0) {
            return ResponseEntity.ok(Map.of("message", "Appointment canceled"));
        } else {
            return ResponseEntity.ok(Map.of("message", "Failed to cancel appointment"));
        }
    }

    @Override
    public ResponseEntity<?> getSpecialities(){
        return ResponseEntity.ok(doctorRepository.findDistinctSpecialities());
    }



   public ResponseEntity<?> updateAppointmentStatus(String authHeader, String appointmentId, String status){
    if (authHeader == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        Optional<User> userOptional = jwtUtil.extractAllClaims(token);
        if (!userOptional.isPresent() || !userOptional.get().getRole().equals(Role.DOCTOR))
            return ResponseEntity.status(401).body("Unauthorized");
        
        User user = userOptional.get();
        Doctor doctor = doctorRepository.findByUser(user).getFirst();

        Appointment appointment = appointmentRepository.getReferenceById(UUID.fromString(appointmentId));

        if(!appointment.getDoctor().equals(doctor)){
            return ResponseEntity.status(401).body("Unauthorized");
        }
System.out.println(AppointmentStatus.valueOf(status));
        appointment.setStatus(AppointmentStatus.valueOf(status));
        appointmentRepository.save(appointment);

        return ResponseEntity.ok().build();
   }




}
