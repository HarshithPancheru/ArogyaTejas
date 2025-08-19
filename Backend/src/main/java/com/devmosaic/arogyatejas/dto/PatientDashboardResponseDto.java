package com.devmosaic.arogyatejas.dto;

import java.util.List;

import com.devmosaic.arogyatejas.model.Appointment;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class PatientDashboardResponseDto {
    private String firstName;
    private String lastName;
    private List<Appointment> appointments;
}

