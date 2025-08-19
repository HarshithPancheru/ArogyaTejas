package com.devmosaic.arogyatejas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devmosaic.arogyatejas.model.Appointment;
import com.devmosaic.arogyatejas.model.Doctor;
import com.devmosaic.arogyatejas.model.Patient;

import java.time.LocalDateTime;
import java.util.List;


public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctor(Doctor doctor);

    List<Appointment> findByPatient(Patient patient);
 

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND a.dateTime > :now")
    List<Appointment> findFutureAppointmentsByDoctor(@Param("doctorId") Long doctorId, @Param("now") LocalDateTime now);

    @Modifying
@Query("UPDATE Appointment a SET a.status = :status WHERE a.doctor.id = :doctorId AND a.patient.id = :patientId")
int setStatus(@Param("doctorId") Long doctorId, @Param("patientId") Long patientId, @Param("status") String status);


 }
