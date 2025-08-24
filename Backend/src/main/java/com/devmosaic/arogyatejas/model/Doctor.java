// Source code is decompiled from a .class file using FernFlower decompiler.
package com.devmosaic.arogyatejas.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    private String speciality;

    @Column(unique = true)
    private String licenseNumber;

    private Integer experienceYears;

    private LocalDateTime availabilityStart;

    private LocalDateTime availabilityEnd;
}
