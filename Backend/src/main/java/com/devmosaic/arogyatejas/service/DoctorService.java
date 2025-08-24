// Source code is decompiled from a .class file using FernFlower decompiler.
package com.devmosaic.arogyatejas.service;


import java.util.UUID;

import org.springframework.http.ResponseEntity;

public interface DoctorService { 
   public ResponseEntity<?> loadDashboard(String token);   
   public ResponseEntity<?> getSpecialities();   

   public ResponseEntity<?> setStatus(String authHeader, UUID id);

   public ResponseEntity<?> updateAppointmentStatus(String authHeader, String appointmentId, String status);
}
