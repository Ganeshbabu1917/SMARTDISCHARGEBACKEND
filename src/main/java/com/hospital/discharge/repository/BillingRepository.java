package com.hospital.discharge.repository;

import com.hospital.discharge.model.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {
    Optional<Billing> findByPatientId(String patientId);
    List<Billing> findByPaymentStatus(String status);
    
    @Query("SELECT SUM(b.totalAmount) FROM Billing b")
    Double getTotalRevenue();
    
    @Query("SELECT SUM(b.dueAmount) FROM Billing b")
    Double getTotalDue();
    
    @Query("SELECT COUNT(b) FROM Billing b WHERE b.paymentStatus = 'Pending'")
    Long countPendingPayments();
}