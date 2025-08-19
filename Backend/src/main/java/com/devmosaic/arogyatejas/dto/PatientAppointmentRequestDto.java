package com.devmosaic.arogyatejas.dto;

import java.time.LocalDate;

import io.micrometer.common.lang.NonNull;
import lombok.Data;

@Data
public class PatientAppointmentRequestDto {
    @NonNull
    private LocalDate date;
    
    @NonNull
    private Long doctorId;
}
