// Source code is decompiled from a .class file using FernFlower decompiler.
package com.devmosaic.arogyatejas.repository;

import com.devmosaic.arogyatejas.model.Patient;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
   boolean existsByEmail(String email);

   Optional<Patient> findByEmail(String email);
}
