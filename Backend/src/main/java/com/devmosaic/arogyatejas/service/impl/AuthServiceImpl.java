// Source code is decompiled from a .class file using FernFlower decompiler.
package com.devmosaic.arogyatejas.service.impl;

import com.devmosaic.arogyatejas.dto.AdminRegistrationDto;
import com.devmosaic.arogyatejas.dto.AuthResponseDto;
import com.devmosaic.arogyatejas.dto.DoctorRegistrationDto;
import com.devmosaic.arogyatejas.dto.LoginDto;
import com.devmosaic.arogyatejas.dto.PatientRegistrationDto;
import com.devmosaic.arogyatejas.exception.EmailAlreadyExistsException;
import com.devmosaic.arogyatejas.exception.InvalidEmailOrPasswordException;
import com.devmosaic.arogyatejas.model.Admin;
import com.devmosaic.arogyatejas.model.Doctor;
import com.devmosaic.arogyatejas.model.Patient;
import com.devmosaic.arogyatejas.repository.AdminRepository;
import com.devmosaic.arogyatejas.repository.DoctorRepository;
import com.devmosaic.arogyatejas.repository.PatientRepository;
import com.devmosaic.arogyatejas.security.JwtUtil;
import com.devmosaic.arogyatejas.service.AuthService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
   @Autowired
   private PatientRepository patientRepository;
   @Autowired
   private DoctorRepository doctorRepository;
   @Autowired
   private AdminRepository adminRepository;
   @Autowired
   private BCryptPasswordEncoder passwordEncoder;
   @Autowired
   private JwtUtil jwtUtil;

   public ResponseEntity<AuthResponseDto> registerPatient(PatientRegistrationDto dto) {
      if (!this.patientRepository.existsByEmail(dto.getEmail()) && !this.adminRepository.existsByEmail(dto.getEmail()) && !this.doctorRepository.existsByEmail(dto.getEmail())) {
         Patient patient = new Patient();
         patient.setFirstName(dto.getFirstName());
         patient.setLastName(dto.getLastName());
         patient.setEmail(dto.getEmail());
         patient.setPassword(this.passwordEncoder.encode(dto.getPassword()));
         Patient savedPatient = (Patient)this.patientRepository.save(patient);
         String token = this.jwtUtil.generateToken(savedPatient.getId(), "PATIENT");
         return ResponseEntity.ok(new AuthResponseDto(token, "PATIENT", "Registration successful"));
      } else {
         throw new EmailAlreadyExistsException();
      }
   }

   public ResponseEntity<AuthResponseDto> registerDoctor(DoctorRegistrationDto dto) {
      if (!this.patientRepository.existsByEmail(dto.getEmail()) && !this.adminRepository.existsByEmail(dto.getEmail()) && !this.doctorRepository.existsByEmail(dto.getEmail())) {
         Doctor doctor = new Doctor();
         doctor.setFirstName(dto.getFirstName());
         doctor.setLastName(dto.getLastName());
         doctor.setEmail(dto.getEmail());
         doctor.setExperience(dto.getExperience());
         doctor.setSpeciality(dto.getSpecialization());
         doctor.setPassword(this.passwordEncoder.encode(dto.getPassword()));
         Doctor savedDoctor = (Doctor)this.doctorRepository.save(doctor);
         String token = this.jwtUtil.generateToken(savedDoctor.getId(), "DOCTOR");
         return ResponseEntity.ok(new AuthResponseDto(token, "DOCTOR", "Registration successful"));
      } else {
         throw new EmailAlreadyExistsException();
      }
   }

   public ResponseEntity<AuthResponseDto> registerAdmin(AdminRegistrationDto dto) {
      if (!this.patientRepository.existsByEmail(dto.getEmail()) && !this.adminRepository.existsByEmail(dto.getEmail()) && !this.doctorRepository.existsByEmail(dto.getEmail())) {
         Admin admin = new Admin();
         admin.setFirstName(dto.getFirstName());
         admin.setLastName(dto.getLastName());
         admin.setEmail(dto.getEmail());
         admin.setPassword(this.passwordEncoder.encode(dto.getPassword()));
         Admin savedAdmin = (Admin)this.adminRepository.save(admin);
         String token = this.jwtUtil.generateToken(savedAdmin.getId(), "ADMIN");
         return ResponseEntity.ok(new AuthResponseDto(token, "ADMIN", "Registration successful"));
      } else {
         return ResponseEntity.ok(new AuthResponseDto((String)null, (String)null, "Registration failed"));
      }
   }

   public ResponseEntity<AuthResponseDto> login(LoginDto dto) {
      String role = null;
      String encodedPassword = null;
      Long userId = null;
      Optional<Admin> adminOpt = this.adminRepository.findByEmail(dto.getEmail());
      if (adminOpt.isPresent()) {
         Admin admin = (Admin)adminOpt.get();
         encodedPassword = admin.getPassword();
         userId = admin.getId();
         role = "ADMIN";
      } else {
         Optional<Doctor> doctorOpt = this.doctorRepository.findByEmail(dto.getEmail());
         if (doctorOpt.isPresent()) {
            Doctor doctor = (Doctor)doctorOpt.get();
            encodedPassword = doctor.getPassword();
            userId = doctor.getId();
            role = "DOCTOR";
         } else {
            Optional<Patient> patientOpt = this.patientRepository.findByEmail(dto.getEmail());
            if (!patientOpt.isPresent()) {
               throw new InvalidEmailOrPasswordException();
            }

            Patient patient = (Patient)patientOpt.get();
            encodedPassword = patient.getPassword();
            userId = patient.getId();
            role = "PATIENT";
         }
      }

      if (!this.passwordEncoder.matches(dto.getPassword(), encodedPassword)) {
         throw new InvalidEmailOrPasswordException();
      } else {
         String token = this.jwtUtil.generateToken(userId, role);
         return ResponseEntity.ok(new AuthResponseDto(token, role, "Login successful as " + role));
      }
   }

   

}
