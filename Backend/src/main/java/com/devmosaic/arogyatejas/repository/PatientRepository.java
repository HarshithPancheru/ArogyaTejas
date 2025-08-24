package com.devmosaic.arogyatejas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devmosaic.arogyatejas.model.Patient;
import com.devmosaic.arogyatejas.model.User;

import java.util.UUID;
import java.util.List;


public interface PatientRepository extends JpaRepository<Patient, UUID> {
    List<Patient> findByUser(User user);
}
