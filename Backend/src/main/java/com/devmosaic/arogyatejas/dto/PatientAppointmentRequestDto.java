package com.devmosaic.arogyatejas.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import io.micrometer.common.lang.NonNull;
import lombok.Data;

@Data
public class PatientAppointmentRequestDto {
    @NonNull
    private LocalDateTime date;
    
    @NonNull
    private UUID doctorId;
}
