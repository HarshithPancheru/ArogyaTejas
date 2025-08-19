package com.devmosaic.arogyatejas.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DoctorAvailabilityDto {
    private String firstName;
    private String lastName;
    private String speciality;
    private int experience;
    private LocalDate nextAvailability;
}