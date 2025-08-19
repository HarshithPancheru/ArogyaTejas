// Source code is decompiled from a .class file using FernFlower decompiler.
package com.devmosaic.arogyatejas.repository;

import com.devmosaic.arogyatejas.model.Admin;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
   boolean existsByEmail(String email);

   Optional<Admin> findByEmail(String email);
}
