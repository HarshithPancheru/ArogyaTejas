// Source code is decompiled from a .class file using FernFlower decompiler.
package com.devmosaic.arogyatejas.service;


import org.springframework.http.ResponseEntity;

public interface DoctorService { 
   public ResponseEntity<?> loadDashboard(String token);   

   public ResponseEntity<?> setStatus(String authHeader, Long id);
}
