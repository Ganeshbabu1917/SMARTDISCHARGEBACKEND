package com.hospital.discharge.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "billing")
public class Billing {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "patient_id", nullable = false)
    private String patientId;
    
    @Column(name = "patient_name")
    private String patientName;
    
    @Column(name = "admission_fees")
    private Double admissionFees = 500.0;
    
    @Column(name = "doctor_fees")
    private Double doctorFees = 1000.0;
    
    @Column(name = "medicine_fees")
    private Double medicineFees = 0.0;
    
    @Column(name = "lab_fees")
    private Double labFees = 0.0;
    
    @Column(name = "room_fees")
    private Double roomFees = 0.0;
    
    @Column(name = "other_fees")
    private Double otherFees = 0.0;
    
    @Column(name = "total_amount")
    private Double totalAmount = 0.0;
    
    @Column(name = "paid_amount")
    private Double paidAmount = 0.0;
    
    @Column(name = "due_amount")
    private Double dueAmount = 0.0;
    
    @Column(name = "payment_status")
    private String paymentStatus = "Pending";
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Constructors
    public Billing() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public Billing(String patientId, String patientName) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.admissionFees = 500.0;
        this.doctorFees = 1000.0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        calculateTotal();
    }
    
    // Calculate total amount
    public void calculateTotal() {
        this.totalAmount = admissionFees + doctorFees + medicineFees + labFees + roomFees + otherFees;
        this.dueAmount = this.totalAmount - this.paidAmount;
        
        if (this.dueAmount <= 0) {
            this.paymentStatus = "Paid";
        } else if (this.paidAmount > 0) {
            this.paymentStatus = "Partial";
        } else {
            this.paymentStatus = "Pending";
        }
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }
    
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    
    public Double getAdmissionFees() { return admissionFees; }
    public void setAdmissionFees(Double admissionFees) { 
        this.admissionFees = admissionFees; 
        calculateTotal();
    }
    
    public Double getDoctorFees() { return doctorFees; }
    public void setDoctorFees(Double doctorFees) { 
        this.doctorFees = doctorFees; 
        calculateTotal();
    }
    
    public Double getMedicineFees() { return medicineFees; }
    public void setMedicineFees(Double medicineFees) { 
        this.medicineFees = medicineFees; 
        calculateTotal();
    }
    
    public Double getLabFees() { return labFees; }
    public void setLabFees(Double labFees) { 
        this.labFees = labFees; 
        calculateTotal();
    }
    
    public Double getRoomFees() { return roomFees; }
    public void setRoomFees(Double roomFees) { 
        this.roomFees = roomFees; 
        calculateTotal();
    }
    
    public Double getOtherFees() { return otherFees; }
    public void setOtherFees(Double otherFees) { 
        this.otherFees = otherFees; 
        calculateTotal();
    }
    
    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
    
    public Double getPaidAmount() { return paidAmount; }
    public void setPaidAmount(Double paidAmount) { 
        this.paidAmount = paidAmount; 
        calculateTotal();
    }
    
    public Double getDueAmount() { return dueAmount; }
    public void setDueAmount(Double dueAmount) { this.dueAmount = dueAmount; }
    
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}