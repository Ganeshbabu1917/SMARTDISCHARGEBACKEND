package com.hospital.discharge.model;

import java.time.LocalDate;
import java.util.List;

public class DischargeSummary {
    private String patientId;
    private String patientName;
    private Integer age;
    private String gender;
    private String diagnosis;
    private LocalDate admissionDate;
    private LocalDate dischargeDate;
    private String clinicalSummary;
    private String patientFriendlySummary;
    private List<String> insights;
    private String medications;
    private String followUpPlan;
    private String generatedDate;
    private String attendingPhysician;

    // Constructor
    public DischargeSummary() {
        this.generatedDate = LocalDate.now().toString();
        this.attendingPhysician = "Dr. Sarah Johnson";
    }

    // ========== GETTERS ==========
    public String getPatientId() { return patientId; }
    public String getPatientName() { return patientName; }
    public Integer getAge() { return age; }
    public String getGender() { return gender; }
    public String getDiagnosis() { return diagnosis; }
    public LocalDate getAdmissionDate() { return admissionDate; }
    public LocalDate getDischargeDate() { return dischargeDate; }
    public String getClinicalSummary() { return clinicalSummary; }
    public String getPatientFriendlySummary() { return patientFriendlySummary; }
    public List<String> getInsights() { return insights; }
    public String getMedications() { return medications; }
    public String getFollowUpPlan() { return followUpPlan; }
    public String getGeneratedDate() { return generatedDate; }
    public String getAttendingPhysician() { return attendingPhysician; }

    // ========== SETTERS ==========
    public void setPatientId(String patientId) { this.patientId = patientId; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public void setAge(Integer age) { this.age = age; }
    public void setGender(String gender) { this.gender = gender; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public void setAdmissionDate(LocalDate admissionDate) { this.admissionDate = admissionDate; }
    public void setDischargeDate(LocalDate dischargeDate) { this.dischargeDate = dischargeDate; }
    public void setClinicalSummary(String clinicalSummary) { this.clinicalSummary = clinicalSummary; }
    public void setPatientFriendlySummary(String patientFriendlySummary) { 
        this.patientFriendlySummary = patientFriendlySummary; 
    }
    public void setInsights(List<String> insights) { this.insights = insights; }
    public void setMedications(String medications) { this.medications = medications; }
    public void setFollowUpPlan(String followUpPlan) { this.followUpPlan = followUpPlan; }
    public void setGeneratedDate(String generatedDate) { this.generatedDate = generatedDate; }
    public void setAttendingPhysician(String attendingPhysician) { 
        this.attendingPhysician = attendingPhysician; 
    }
    
    // ✅ ADD THIS - Helpful for debugging
    @Override
    public String toString() {
        return "DischargeSummary{" +
                "patientId='" + patientId + '\'' +
                ", patientName='" + patientName + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                '}';
    }
}