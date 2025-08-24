// Source code is decompiled from a .class file using FernFlower decompiler.
package com.devmosaic.arogyatejas.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Table(name = "patients")
public class Patient {
    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID patientId;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    private LocalDate dateOfBirth;

    private String gender;

    private String phone;

    private String address;
}
