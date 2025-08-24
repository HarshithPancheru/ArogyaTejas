package com.devmosaic.arogyatejas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devmosaic.arogyatejas.model.Appointment;
import com.devmosaic.arogyatejas.model.AppointmentStatus;
import com.devmosaic.arogyatejas.model.Doctor;
import com.devmosaic.arogyatejas.model.Patient;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

       @Query("SELECT a FROM Appointment a " +
                     "WHERE a.doctor.id = :doctorId " +
                     "AND a.appointmentDateTime > :now " + // <-- Corrected to match the entity field
                     "ORDER BY a.appointmentDateTime ASC") // <-- Also corrected here
       List<Appointment> findFutureAppointmentsByDoctor(@Param("doctorId") UUID doctorId,
                     @Param("now") LocalDateTime now);

       @Query("SELECT a FROM Appointment a " +
                     "WHERE a.patient.id = :patientId " +
                     "AND a.appointmentDateTime > :now " + // <-- Corrected to match the entity field
                     "ORDER BY a.appointmentDateTime ASC") // <-- Also corrected here
       List<Appointment> findFutureAppointmentsByPatient(@Param("patientId") UUID patientId,
                     @Param("now") LocalDateTime now);

       @Query("SELECT a FROM Appointment a " +
                     "JOIN FETCH a.patient p " +
                     // CORRECTED: Added the alias 'd' here
                     "JOIN FETCH a.doctor d " +
                     "JOIN FETCH d.user u " +
                     "WHERE a.doctor = :doctor " +
                     "ORDER BY a.appointmentDateTime DESC")
       List<Appointment> findByDoctor(@Param("doctor") Doctor doctor);

       @Query("SELECT a FROM Appointment a " +
                     "JOIN FETCH a.doctor d " +
                     "JOIN FETCH d.user u " +
                     "WHERE a.patient = :patient " +
                     "ORDER BY a.appointmentDateTime DESC")
       List<Appointment> findByPatient(@Param("patient") Patient patient);

       @Modifying
       @Query("UPDATE Appointment a " +
                     "SET a.status = :status, a.updatedAt = :updatedAt " +
                     "WHERE a.doctor.id = :doctorId " +
                     "AND a.appointmentId = :appointmentId")
       int updateStatusAndTime(@Param("doctorId") UUID doctorId,
                     @Param("appointmentId") UUID appointmentId,
                     @Param("status") AppointmentStatus status,
                     @Param("updatedAt") LocalDateTime updatedAt);

       @Transactional
       int deleteByAppointmentId(UUID appointmentId);
}
