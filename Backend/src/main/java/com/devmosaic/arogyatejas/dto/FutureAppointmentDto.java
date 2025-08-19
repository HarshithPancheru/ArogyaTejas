package com.devmosaic.arogyatejas.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FutureAppointmentDto {
    private Long appointmentId;
    private String patientFirstName;
    private String patientLastName;
    private String patientPhone;
    private LocalDateTime appointmentDate;
    private String status;
}
