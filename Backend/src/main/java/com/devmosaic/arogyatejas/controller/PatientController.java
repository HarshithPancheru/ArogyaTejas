package com.devmosaic.arogyatejas.controller;

import com.devmosaic.arogyatejas.dto.PatientAppointmentRequestDto;
import com.devmosaic.arogyatejas.service.PatientService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private  PatientService patientService;

    

    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard(@RequestHeader("Authorization") String authHeader) {
        return patientService.loadDashboard(authHeader);
    }


    @GetMapping("/doctors-available")
    public ResponseEntity<?> getAvailableDoctors(@RequestHeader("Authorization") String authHeader) {
        return patientService.getAvailableDoctors(authHeader);
    }

    @PostMapping("/appointment")
    public ResponseEntity<?> setAppointment(@RequestHeader("Authorization") String authHeader,@Valid @RequestBody PatientAppointmentRequestDto dto) {
        return patientService.setAppointment(authHeader, dto);
    }   
}
