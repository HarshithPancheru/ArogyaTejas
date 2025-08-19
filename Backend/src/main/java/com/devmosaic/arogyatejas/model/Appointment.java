// Source code is decompiled from a .class file using FernFlower decompiler.
package com.devmosaic.arogyatejas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import lombok.Data;

@Entity
@Data
public class Appointment {
   @Id
   @GeneratedValue(
      strategy = GenerationType.IDENTITY
   )
   private Long id;
   @ManyToOne
   private Patient patient;
   @ManyToOne
   private Doctor doctor;
   private LocalDateTime createdOn;
   private String status;
   private LocalDateTime dateTime;

   @PrePersist
   public void prePersist() {
      if (this.createdOn == null) {
         ZonedDateTime istNow = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
         this.createdOn = istNow.toLocalDateTime();
      }

   }
}
