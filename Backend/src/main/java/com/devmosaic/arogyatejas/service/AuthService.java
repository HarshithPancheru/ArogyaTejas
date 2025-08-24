// Source code is decompiled from a .class file using FernFlower decompiler.
package com.devmosaic.arogyatejas.service;

import com.devmosaic.arogyatejas.dto.AdminRegistrationDto;
import com.devmosaic.arogyatejas.dto.AuthResponseDto;
import com.devmosaic.arogyatejas.dto.DoctorRegistrationDto;
import com.devmosaic.arogyatejas.dto.LoginDto;
import com.devmosaic.arogyatejas.dto.PatientRegistrationDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
   ResponseEntity<AuthResponseDto> registerPatient(PatientRegistrationDto dto);

   ResponseEntity<?> registerDoctor(String authHeader, DoctorRegistrationDto dto);

   ResponseEntity<AuthResponseDto> registerAdmin(AdminRegistrationDto dto);

   ResponseEntity<AuthResponseDto> login(LoginDto dto);
}
