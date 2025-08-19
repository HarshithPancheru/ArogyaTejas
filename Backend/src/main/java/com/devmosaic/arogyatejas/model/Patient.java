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

@Data
@Entity
public class Patient {
   @Id
   @GeneratedValue(
      strategy = GenerationType.IDENTITY
   )
   private Long id;
   private String firstName;
   private String lastName;
   private LocalDate dob;
   private String mobile;
   @Column(
      unique = true
   )
   private String email;
   private String password;
   private String gender;
   private LocalDateTime createdOn;

   @PrePersist
   public void prePersist() {
      if (this.createdOn == null) {
         ZonedDateTime istNow = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
         this.createdOn = istNow.toLocalDateTime();
      }

   }
}
