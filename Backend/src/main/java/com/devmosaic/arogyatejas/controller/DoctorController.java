
package com.devmosaic.arogyatejas.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devmosaic.arogyatejas.service.DoctorService;


@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    private  DoctorService doctorService;


    @GetMapping("/dashboard")
    public ResponseEntity<?> getAvailableDoctors(@RequestHeader("Authorization") String authHeader) {
        return doctorService.loadDashboard(authHeader);
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> setAppointment(@RequestHeader("Authorization") String authHeader,@RequestParam Long id) {
        return doctorService.setStatus(authHeader,id);
    }   
}
