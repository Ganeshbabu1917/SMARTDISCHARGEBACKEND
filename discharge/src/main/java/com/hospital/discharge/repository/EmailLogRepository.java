package com.hospital.discharge.repository;

import com.hospital.discharge.model.EmailLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmailLogRepository extends JpaRepository<EmailLog, Long> {
    List<EmailLog> findByPatientId(String patientId);
    List<EmailLog> findByStatus(String status);
}