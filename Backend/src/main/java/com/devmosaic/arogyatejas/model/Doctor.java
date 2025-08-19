// Source code is decompiled from a .class file using FernFlower decompiler.
package com.devmosaic.arogyatejas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import lombok.Data;

@Entity
@Data
public class Doctor {
   @Id
   @GeneratedValue(
      strategy = GenerationType.IDENTITY
   )
   private Long id;
   private String speciality;
   private String firstName;
   private String lastName;
   private String password;
   private LocalDate dob;
   @Column(
      unique = true
   )
   private String email;
   private String mobile;
   private String gender;
   private Integer experience;
   private String address;
   private LocalDateTime createdOn;

   private LocalDate nextAvailability;

   @PrePersist
   public void prePersist() {
      if (this.createdOn == null) {
         ZonedDateTime istNow = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
         this.createdOn = istNow.toLocalDateTime();
      }

   }
}
