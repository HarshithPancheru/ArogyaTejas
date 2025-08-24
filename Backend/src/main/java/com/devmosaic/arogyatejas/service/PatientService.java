// Source code is decompiled from a .class file using FernFlower decompiler.
package com.devmosaic.arogyatejas.service;

import com.devmosaic.arogyatejas.dto.PatientAppointmentRequestDto;

import org.springframework.http.ResponseEntity;

public interface PatientService { 
   public ResponseEntity<?> loadDashboard(String authHeader);
   public ResponseEntity<?> getAvailableDoctors(String speciality);
   public ResponseEntity<?> cancelAppointment(String authHeader, String appointmentId);
   public ResponseEntity<?> setAppointment(String authHeader, PatientAppointmentRequestDto dto);
}
