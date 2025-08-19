package com.devmosaic.arogyatejas.dto;

import lombok.Data;

@Data
public class PatientAppointmentResponseDto {
    
    private Long id;
    private String time;
    private String date;
    private String status;
}

