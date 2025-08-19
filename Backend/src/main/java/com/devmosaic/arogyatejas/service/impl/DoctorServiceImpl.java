package com.devmosaic.arogyatejas.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.devmosaic.arogyatejas.dto.FutureAppointmentDto;
import com.devmosaic.arogyatejas.model.Appointment;
import com.devmosaic.arogyatejas.model.Patient;
import com.devmosaic.arogyatejas.repository.AppointmentRepository;
import com.devmosaic.arogyatejas.security.JwtUtil;
import com.devmosaic.arogyatejas.service.*;

import jakarta.transaction.Transactional;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<?> loadDashboard(String authHeader) {

        if(authHeader==null) return ResponseEntity.status(401).body("Unauthorized");
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        Map<String, Object> claims = jwtUtil.extractAllClaims(token);

        // Get userId from claims
        Long userId = Long.parseLong(claims.get("userId").toString());

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
        List<Appointment> appointments = appointmentRepository.findFutureAppointmentsByDoctor(userId, now);

        appointments.stream().map(appointment -> {
            Patient patient = appointment.getPatient();
            FutureAppointmentDto dto = new FutureAppointmentDto();
            dto.setAppointmentId(appointment.getId());
            dto.setPatientFirstName(patient.getFirstName());
            dto.setPatientLastName(patient.getLastName());
            dto.setPatientPhone(patient.getMobile());
            dto.setAppointmentDate(appointment.getCreatedOn());
            dto.setStatus(appointment.getStatus());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(appointments);
    }

    @Transactional
    public ResponseEntity<?> setStatus(String authHeader, Long id) {

        
        if(authHeader==null) return ResponseEntity.status(401).body("Unauthorized");
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        Map<String, Object> claims = jwtUtil.extractAllClaims(token);

        // Get userId from claims
        Long userId = Long.parseLong(claims.get("userId").toString());

        int result = appointmentRepository.setStatus(userId, id, "canceled");

        if (result > 0)
            return ResponseEntity.ok(Map.of("message", "appointment canceled"));
        else
            return ResponseEntity.ok(Map.of("message", "failed to cancel appointment"));

    }
}