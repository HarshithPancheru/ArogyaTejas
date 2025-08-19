// Source code is decompiled from a .class file using FernFlower decompiler.
package com.devmosaic.arogyatejas.repository;

import com.devmosaic.arogyatejas.model.Doctor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
   boolean existsByEmail(String email);

   Optional<Doctor> findByEmail(String email);
}
