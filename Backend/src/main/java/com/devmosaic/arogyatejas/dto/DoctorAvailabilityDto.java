package com.devmosaic.arogyatejas.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DoctorAvailabilityDto {
    private UUID doctorId;
    private String fullName;
    private String speciality;
    private Integer experience;
    private LocalDateTime nextAvailabilityStart;
    private LocalDateTime nextAvailabilityEnd;
}