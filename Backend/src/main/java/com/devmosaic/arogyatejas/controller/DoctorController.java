
package com.devmosaic.arogyatejas.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devmosaic.arogyatejas.dto.UpdateStatusDto;
import com.devmosaic.arogyatejas.service.DoctorService;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/dashboard")
    public ResponseEntity<?> getAppointments(@RequestHeader("Authorization") String authHeader) {
        return doctorService.loadDashboard(authHeader);
    }

    @GetMapping("/specialities")
    public ResponseEntity<?> getSpecialities() {
        return doctorService.getSpecialities();
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> setAppointment(@RequestHeader("Authorization") String authHeader, @RequestParam UUID id) {
        return doctorService.setStatus(authHeader, id);
    }

    @PutMapping("/appointment/{appointmentId}/status")
    public ResponseEntity<Void> updateAppointmentStatus(@RequestHeader("Authorization") String authHeader,
            @PathVariable String appointmentId,
            @RequestBody UpdateStatusDto statusDto) {

        // Delegate the actual logic to a service layer
        doctorService.updateAppointmentStatus(authHeader, appointmentId, statusDto.getStatus());

        // Return an HTTP 200 OK response with no body
        return ResponseEntity.ok().build();
    }
}
