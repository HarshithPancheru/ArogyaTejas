package com.devmosaic.arogyatejas.controller;

import com.devmosaic.arogyatejas.dto.PatientRegistrationDto;
import com.devmosaic.arogyatejas.dto.AdminRegistrationDto;
import com.devmosaic.arogyatejas.dto.AuthResponseDto;
import com.devmosaic.arogyatejas.dto.DoctorRegistrationDto;
import com.devmosaic.arogyatejas.dto.LoginDto;
import com.devmosaic.arogyatejas.service.AuthService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/patient/register")
    public ResponseEntity<AuthResponseDto> registerPatient(@Valid @RequestBody PatientRegistrationDto dto) {
        return authService.registerPatient(dto);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<AuthResponseDto> registerAdmin(@Valid @RequestBody AdminRegistrationDto dto) {
        return authService.registerAdmin(dto);
    }

    @PostMapping("/doctor/register")
    public ResponseEntity<?> registerDoctor(@RequestHeader("Authorization") String authHeader,@Valid @RequestBody DoctorRegistrationDto dto) {
        return authService.registerDoctor(authHeader, dto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginDto dto) {
        return authService.login(dto);
    }
}
