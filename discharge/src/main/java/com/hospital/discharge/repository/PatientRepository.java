package com.hospital.discharge.repository;

import com.hospital.discharge.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
    // Find patient by custom patient ID
    Optional<Patient> findByPatientId(String patientId);
    
    // ✅ ADD THIS METHOD - Your PatientController.java needs it!
    boolean existsByPatientId(String patientId);
}