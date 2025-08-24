package com.devmosaic.arogyatejas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devmosaic.arogyatejas.model.Doctor;
import com.devmosaic.arogyatejas.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    List<Doctor> findBySpeciality(String speciality);
    List<Doctor> findByUser(User user);

    @Query("SELECT DISTINCT d.speciality FROM Doctor d ORDER BY d.speciality ASC")
    List<String> findDistinctSpecialities();

    @Query("SELECT d FROM Doctor d " +
           "WHERE d.speciality = :speciality " +
           "AND d.availabilityEnd > :now " +
           "ORDER BY d.user.fullName ASC")
    List<Doctor> findAvailableDoctorsBySpeciality(@Param("speciality") String speciality,
                                                  @Param("now") LocalDateTime now);
}