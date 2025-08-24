package com.devmosaic.arogyatejas.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.devmosaic.arogyatejas.model.AppointmentStatus;

import lombok.Data;

@Data
public class FutureAppointmentDto {
    private UUID appointmentId;
    private String patientFullName;
    private String patientPhone;
    private LocalDateTime appointmentDate;
    AppointmentStatus status;
}
