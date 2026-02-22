package com.hospital.discharge.controller;

import com.hospital.discharge.model.Billing;
import com.hospital.discharge.repository.BillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/billing")
@CrossOrigin(origins = "http://ec2-13-203-67-191.ap-south-1.compute.amazonaws.com:3000")
public class BillingController {

    @Autowired
    private BillingRepository billingRepository;

    // GET all billing records
    @GetMapping
    public ResponseEntity<List<Billing>> getAllBilling() {
        try {
            List<Billing> bills = billingRepository.findAll();
            return new ResponseEntity<>(bills, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET billing by patient ID
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<?> getBillingByPatientId(@PathVariable String patientId) {
        try {
            Optional<Billing> billing = billingRepository.findByPatientId(patientId);
            if (billing.isPresent()) {
                return new ResponseEntity<>(billing.get(), HttpStatus.OK);
            } else {
                // Create default billing if not exists
                return new ResponseEntity<>("Billing not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), 
                                      HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // CREATE billing for patient
    @PostMapping("/create")
    public ResponseEntity<?> createBilling(@RequestBody Billing billing) {
        try {
            // Check if billing already exists
            Optional<Billing> existingBilling = billingRepository.findByPatientId(billing.getPatientId());
            if (existingBilling.isPresent()) {
                return new ResponseEntity<>("Billing already exists for this patient", 
                                          HttpStatus.BAD_REQUEST);
            }
            
            billing.setCreatedAt(LocalDateTime.now());
            billing.setUpdatedAt(LocalDateTime.now());
            billing.calculateTotal();
            
            Billing savedBilling = billingRepository.save(billing);
            return new ResponseEntity<>(savedBilling, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating billing: " + e.getMessage(), 
                                      HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // UPDATE billing
    @PutMapping("/update/{patientId}")
    public ResponseEntity<?> updateBilling(@PathVariable String patientId, @RequestBody Billing billing) {
        try {
            Optional<Billing> existingBilling = billingRepository.findByPatientId(patientId);
            if (existingBilling.isPresent()) {
                Billing bill = existingBilling.get();
                bill.setAdmissionFees(billing.getAdmissionFees());
                bill.setDoctorFees(billing.getDoctorFees());
                bill.setMedicineFees(billing.getMedicineFees());
                bill.setLabFees(billing.getLabFees());
                bill.setRoomFees(billing.getRoomFees());
                bill.setOtherFees(billing.getOtherFees());
                bill.setPaidAmount(billing.getPaidAmount());
                bill.setUpdatedAt(LocalDateTime.now());
                bill.calculateTotal();
                
                Billing updatedBilling = billingRepository.save(bill);
                return new ResponseEntity<>(updatedBilling, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Billing not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating billing: " + e.getMessage(), 
                                      HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET dashboard stats
    @GetMapping("/stats")
    public ResponseEntity<?> getBillingStats() {
        try {
            Double totalRevenue = billingRepository.getTotalRevenue();
            Double totalDue = billingRepository.getTotalDue();
            Long pendingCount = billingRepository.countPendingPayments();
            
            if (totalRevenue == null) totalRevenue = 0.0;
            if (totalDue == null) totalDue = 0.0;
            
            return new ResponseEntity<>(new BillingStats(totalRevenue, totalDue, pendingCount), 
                                      HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), 
                                      HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Inner class for stats
    static class BillingStats {
        public Double totalRevenue;
        public Double totalDue;
        public Long pendingPayments;
        
        public BillingStats(Double totalRevenue, Double totalDue, Long pendingPayments) {
            this.totalRevenue = totalRevenue;
            this.totalDue = totalDue;
            this.pendingPayments = pendingPayments;
        }
    }
}
