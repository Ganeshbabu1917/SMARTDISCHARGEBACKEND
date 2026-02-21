package com.hospital.discharge.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "patients")
public class Patient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "patient_id", unique = true, nullable = false)
    private String patientId;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "age")
    private Integer age;
    
    @Column(name = "gender")
    private String gender;
    
    @Column(name = "diagnosis", columnDefinition = "TEXT")
    private String diagnosis;
    
    @Column(name = "admission_date")
    private LocalDate admissionDate;
    
    @Column(name = "discharge_date")
    private LocalDate dischargeDate;
    
    @Column(name = "medications", columnDefinition = "TEXT")
    private String medications;
    
    @Column(name = "vitals", columnDefinition = "TEXT")
    private String vitals;
    
    @Column(name = "lab_results", columnDefinition = "TEXT")
    private String labResults;
    
    @Column(name = "doctor_notes", columnDefinition = "TEXT")
    private String doctorNotes;
    // Add this field to your Patient.java
@Column(name = "email")
private String email;

// Add getter and setter
public String getEmail() { return email; }
public void setEmail(String email) { this.email = email; }

    // Constructors
    public Patient() {}

    public Patient(String patientId, String name, Integer age, String gender, 
                  String diagnosis, LocalDate admissionDate) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.diagnosis = diagnosis;
        this.admissionDate = admissionDate;
    }

    // Getters
    public Long getId() { return id; }
    public String getPatientId() { return patientId; }
    public String getName() { return name; }
    public Integer getAge() { return age; }
    public String getGender() { return gender; }
    public String getDiagnosis() { return diagnosis; }
    public LocalDate getAdmissionDate() { return admissionDate; }
    public LocalDate getDischargeDate() { return dischargeDate; }
    public String getMedications() { return medications; }
    public String getVitals() { return vitals; }
    public String getLabResults() { return labResults; }
    public String getDoctorNotes() { return doctorNotes; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setPatientId(String patientId) { this.patientId = patientId; }
    public void setName(String name) { this.name = name; }
    public void setAge(Integer age) { this.age = age; }
    public void setGender(String gender) { this.gender = gender; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public void setAdmissionDate(LocalDate admissionDate) { this.admissionDate = admissionDate; }
    public void setDischargeDate(LocalDate dischargeDate) { this.dischargeDate = dischargeDate; }
    public void setMedications(String medications) { this.medications = medications; }
    public void setVitals(String vitals) { this.vitals = vitals; }
    public void setLabResults(String labResults) { this.labResults = labResults; }
    public void setDoctorNotes(String doctorNotes) { this.doctorNotes = doctorNotes; }
}