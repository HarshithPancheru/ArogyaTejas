package com.devmosaic.arogyatejas.service.impl;

import com.devmosaic.arogyatejas.dto.*;
import com.devmosaic.arogyatejas.exception.EmailAlreadyExistsException;
import com.devmosaic.arogyatejas.exception.InvalidEmailOrPasswordException;
import com.devmosaic.arogyatejas.model.*;
import com.devmosaic.arogyatejas.repository.*;
import com.devmosaic.arogyatejas.security.JwtUtil;
import com.devmosaic.arogyatejas.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    // ---------------- PATIENT REGISTRATION ----------------
    @Override
    public ResponseEntity<AuthResponseDto> registerPatient(PatientRegistrationDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        // Create user
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        user.setFullName(dto.getFullName());

        user.setRole(Role.PATIENT);

        User savedUser = userRepository.save(user);

        // Create patient profile
        Patient patient = new Patient();
        patient.setUser(savedUser);
        patient.setDateOfBirth(dto.getDateOfBirth());
        patient.setGender(dto.getGender());
        patient.setPhone(dto.getPhone());
        patient.setAddress(dto.getAddress());
        patientRepository.save(patient);

        String token = jwtUtil.generateToken(savedUser);
        return ResponseEntity.ok(new AuthResponseDto(token, "PATIENT", user.getFullName(), "Registration successful"));
    }

    // ---------------- DOCTOR REGISTRATION ----------------
    @Override
    public ResponseEntity<?> registerDoctor(String authHeader, DoctorRegistrationDto dto) {

        if (authHeader == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        Optional<User> userOptional = jwtUtil.extractAllClaims(token);

        if (!userOptional.isPresent() || !userOptional.get().getRole().equals(Role.ADMIN))
            return ResponseEntity.status(401).body("Unauthorized");

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        // Create user
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        user.setFullName(dto.getFullName());
        user.setRole(Role.DOCTOR);

        User savedUser = userRepository.save(user);

        // Create doctor profile
        Doctor doctor = new Doctor();
        doctor.setUser(savedUser);
        doctor.setSpeciality(dto.getSpecialization());
        doctor.setLicenseNumber(dto.getLicenseNumber());
        doctor.setExperienceYears(dto.getExperienceYears());
        doctor.setAvailabilityStart(dto.getAvailabilityStart());
        doctor.setAvailabilityEnd(dto.getAvailabilityEnd());
        doctorRepository.save(doctor);

        token = jwtUtil.generateToken(savedUser);
        return ResponseEntity.ok(new AuthResponseDto(token, "DOCTOR", user.getFullName(), "Registration successful"));
    }

    // ---------------- ADMIN REGISTRATION ----------------
    @Override
    public ResponseEntity<AuthResponseDto> registerAdmin(AdminRegistrationDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        user.setFullName(dto.getFullName());

        user.setRole(Role.ADMIN);

        User savedUser = userRepository.save(user);

        // optional: save admin-specific entity if you keep Admin table
        // Admin admin = new Admin();
        // admin.setUser(savedUser);
        // adminRepository.save(admin);

        String token = jwtUtil.generateToken(savedUser);
        return ResponseEntity
                .ok(new AuthResponseDto(token, Role.ADMIN.toString(), user.getFullName(), "Registration successful"));
    }

    // ---------------- LOGIN ----------------
    @Override
    public ResponseEntity<AuthResponseDto> login(LoginDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(InvalidEmailOrPasswordException::new);

        if (!passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
            throw new InvalidEmailOrPasswordException();
        }

        Role role = user.getRole();

        String token = jwtUtil.generateToken(user);
        return ResponseEntity
                .ok(new AuthResponseDto(token, role.toString(), user.getFullName(), "Login successful as " + role));
    }
}