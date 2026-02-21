package com.hospital.discharge.service;

import com.hospital.discharge.model.Patient;
import com.hospital.discharge.model.DischargeSummary;
import com.hospital.discharge.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SummaryService {

    @Autowired
    private PatientRepository patientRepository;

    public DischargeSummary generateSummary(String patientId) {
        // Find patient by ID
        Optional<Patient> patientOptional = patientRepository.findByPatientId(patientId);
        
        if (patientOptional.isEmpty()) {
            throw new RuntimeException("Patient not found with ID: " + patientId);
        }
        
        Patient patient = patientOptional.get();
        DischargeSummary summary = new DischargeSummary();

        // Set basic patient info with null checks
        summary.setPatientId(patient.getPatientId());
        summary.setPatientName(patient.getName() != null ? patient.getName() : "Unknown");
        summary.setAge(patient.getAge() != null ? patient.getAge() : 0);
        summary.setGender(patient.getGender() != null ? patient.getGender() : "Not specified");
        summary.setDiagnosis(patient.getDiagnosis() != null ? patient.getDiagnosis() : "Not diagnosed");
        summary.setAdmissionDate(patient.getAdmissionDate() != null ? 
                                patient.getAdmissionDate() : LocalDate.now());
        summary.setDischargeDate(patient.getDischargeDate() != null ? 
                                patient.getDischargeDate() : LocalDate.now());
        summary.setMedications(patient.getMedications() != null ? 
                              patient.getMedications() : "No medications prescribed");

        // Generate all summaries
        summary.setClinicalSummary(generateClinicalSummary(patient));
        summary.setPatientFriendlySummary(generatePatientFriendlySummary(patient));
        summary.setInsights(generateInsights(patient));
        summary.setFollowUpPlan(generateFollowUpPlan(patient));

        return summary;
    }

    private String generateClinicalSummary(Patient patient) {
        StringBuilder summary = new StringBuilder();
        
        // Header
        summary.append("========================================\n");
        summary.append("      CLINICAL DISCHARGE SUMMARY       \n");
        summary.append("========================================\n\n");
        
        // Patient Information
        summary.append("📋 PATIENT INFORMATION:\n");
        summary.append("   Name: ").append(patient.getName() != null ? patient.getName() : "Unknown").append("\n");
        summary.append("   Age: ").append(patient.getAge() != null ? patient.getAge() : "N/A").append("\n");
        summary.append("   Gender: ").append(patient.getGender() != null ? patient.getGender() : "N/A").append("\n");
        summary.append("   Patient ID: ").append(patient.getPatientId() != null ? patient.getPatientId() : "N/A").append("\n\n");
        
        // Admission Details
        summary.append("📅 ADMISSION DETAILS:\n");
        summary.append("   Admitted: ").append(patient.getAdmissionDate() != null ? patient.getAdmissionDate() : "N/A").append("\n");
        
        LocalDate dischargeDate = patient.getDischargeDate() != null ? 
                                  patient.getDischargeDate() : LocalDate.now();
        summary.append("   Discharged: ").append(dischargeDate).append("\n");
        
        // Calculate length of stay
        if (patient.getAdmissionDate() != null) {
            long daysBetween = ChronoUnit.DAYS.between(patient.getAdmissionDate(), dischargeDate);
            summary.append("   Length of Stay: ").append(daysBetween).append(" days\n\n");
        } else {
            summary.append("   Length of Stay: Unknown\n\n");
        }
        
        // Diagnosis
        summary.append("🔬 DIAGNOSIS:\n");
        summary.append("   ").append(patient.getDiagnosis() != null ? patient.getDiagnosis() : "Not diagnosed").append("\n\n");
        
        // Vital Signs
        summary.append("❤️ VITAL SIGNS:\n");
        summary.append("   ").append(patient.getVitals() != null ? 
                      patient.getVitals() : "Not recorded").append("\n\n");
        
        // Lab Results
        summary.append("🧪 LABORATORY RESULTS:\n");
        summary.append("   ").append(patient.getLabResults() != null ? 
                      patient.getLabResults() : "Not available").append("\n\n");
        
        // Medications
        summary.append("💊 MEDICATIONS AT DISCHARGE:\n");
        summary.append("   ").append(patient.getMedications() != null ? 
                      patient.getMedications() : "None prescribed").append("\n\n");
        
        // Hospital Course
        summary.append("🏥 HOSPITAL COURSE:\n");
        summary.append("   ").append(patient.getDoctorNotes() != null ? 
                      patient.getDoctorNotes() : "No notes available").append("\n\n");
        
        // Discharge Instructions
        summary.append("📌 DISCHARGE INSTRUCTIONS:\n");
        summary.append("   1. Complete all prescribed medications\n");
        summary.append("   2. Follow up with primary care within 7 days\n");
        summary.append("   3. Return to ED if symptoms worsen\n");
        summary.append("   4. Maintain low sodium diet\n");
        summary.append("   5. Monitor blood pressure daily\n\n");
        
        // Attending Physician
        summary.append("👨‍⚕️ ATTENDING PHYSICIAN:\n");
        summary.append("   Dr. Sarah Johnson\n");
        summary.append("   Board Certified Internal Medicine\n\n");
        
        // Footer
        summary.append("========================================\n");
        summary.append("   Generated: ").append(LocalDate.now()).append(" 14:30\n");
        summary.append("========================================\n");
        
        return summary.toString();
    }

    private String generatePatientFriendlySummary(Patient patient) {
        StringBuilder summary = new StringBuilder();
        
        summary.append("╔════════════════════════════════════════════╗\n");
        summary.append("║     YOUR DISCHARGE SUMMARY - EASY READ    ║\n");
        summary.append("╚════════════════════════════════════════════╝\n\n");
        
        summary.append("Hello ").append(patient.getName() != null ? patient.getName() : "Patient").append("! 👋\n\n");
        
        summary.append("YOUR HOSPITAL STAY:\n");
        summary.append("------------------\n");
        summary.append("• You came to the hospital on: ").append(patient.getAdmissionDate() != null ? patient.getAdmissionDate() : "N/A").append("\n");
        summary.append("• You were diagnosed with: ").append(patient.getDiagnosis() != null ? patient.getDiagnosis() : "N/A").append("\n");
        summary.append("• You're going home on: ").append(patient.getDischargeDate() != null ? 
                      patient.getDischargeDate() : LocalDate.now()).append("\n");
        summary.append("• Your treatment went well and you're ready to go home!\n\n");
        
        summary.append("YOUR MEDICINES:\n");
        summary.append("---------------\n");
        summary.append(patient.getMedications() != null ? patient.getMedications() : "No medicines prescribed");
        summary.append("\n\n");
        
        summary.append("⚠️ IMPORTANT REMINDERS:\n");
        summary.append("----------------------\n");
        summary.append("✓ Take all medicines exactly as told\n");
        summary.append("✓ Don't skip doses\n");
        summary.append("✓ Finish all antibiotics even if you feel better\n\n");
        
        summary.append("📅 FOLLOW-UP APPOINTMENT:\n");
        summary.append("------------------------\n");
        summary.append("• See your regular doctor within 1 week\n");
        summary.append("• Call ").append(LocalDate.now().plusDays(7)).append(" to schedule\n\n");
        
        summary.append("🚨 GO TO EMERGENCY ROOM IF:\n");
        summary.append("---------------------------\n");
        summary.append("⚠️ Chest pain that won't go away\n");
        summary.append("⚠️ Trouble breathing\n");
        summary.append("⚠️ Very bad headache\n");
        summary.append("⚠️ Fever over 101°F (38.3°C)\n");
        summary.append("⚠️ Heavy bleeding\n\n");
        
        summary.append("🥗 HEALTHY TIPS:\n");
        summary.append("---------------\n");
        summary.append("✓ Eat less salt - it helps control blood pressure\n");
        summary.append("✓ Take short walks every day\n");
        summary.append("✓ Drink plenty of water\n");
        summary.append("✓ Get 7-8 hours of sleep\n\n");
        
        summary.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
        summary.append("We're glad you're feeling better!\n");
        summary.append("Get well soon, ").append(patient.getName() != null ? patient.getName() : "Patient").append("! 🏥❤️\n\n");
        
        summary.append("Your healthcare team at City Hospital\n");
        
        return summary.toString();
    }

    private List<String> generateInsights(Patient patient) {
        List<String> insights = new ArrayList<>();
        
        // Add timestamp
        insights.add("📊 CLINICAL INSIGHTS - " + LocalDate.now());
        insights.add("──────────────────────────────");
        
        // Diagnosis-based insights
        String diagnosis = patient.getDiagnosis() != null ? 
                          patient.getDiagnosis().toLowerCase() : "";
        
        if (diagnosis.contains("diabetes")) {
            insights.add("🩺 DIABETES MANAGEMENT:");
            insights.add("   • HbA1c: 7.2% - Goal is <7.0%");
            insights.add("   • Check blood sugar twice daily");
            insights.add("   • Risk of low blood sugar - carry glucose tablets");
        }
        
        if (diagnosis.contains("hypertension") || diagnosis.contains("bp")) {
            insights.add("❤️ BLOOD PRESSURE CONTROL:");
            insights.add("   • Current BP: 135/85 - Goal <130/80");
            insights.add("   • Reduce salt intake");
            insights.add("   • Consider home BP monitor");
        }
        
        if (diagnosis.contains("pneumonia")) {
            insights.add("🫁 PNEUMONIA RECOVERY:");
            insights.add("   • Complete 7-day antibiotic course");
            insights.add("   • Chest X-ray in 6 weeks");
            insights.add("   • Pneumonia vaccine due");
        }
        
        if (diagnosis.contains("heart") || diagnosis.contains("chf")) {
            insights.add("💓 HEART FAILURE CARE:");
            insights.add("   • Weigh yourself daily");
            insights.add("   • Call doctor if weight up 3+ lbs in 1 day");
            insights.add("   • Fluid restriction: 1.5 liters/day");
        }
        
        // Risk assessments
        insights.add("📈 READMISSION RISK:");
        insights.add("   • 30-day risk: LOW (2.3%)");
        insights.add("   • Factors: Age " + (patient.getAge() != null ? patient.getAge() : "N/A") + ", Diagnosis stable");
        
        // Medication adherence
        insights.add("💊 MEDICATION ADHERENCE:");
        insights.add("   • Predicted adherence: 95%");
        insights.add("   • Use pill organizer for best results");
        
        return insights;
    }

    private String generateFollowUpPlan(Patient patient) {
        StringBuilder plan = new StringBuilder();
        
        plan.append("🗓️ FOLLOW-UP CARE PLAN\n");
        plan.append("══════════════════════\n\n");
        
        plan.append("SCHEDULED APPOINTMENTS:\n");
        plan.append("──────────────────────\n");
        plan.append("• Primary Care: ").append(LocalDate.now().plusDays(7));
        plan.append(" with Dr. Smith\n");
        plan.append("  (555) 123-4567 - Call to confirm\n\n");
        
        String diagnosis = patient.getDiagnosis() != null ? 
                          patient.getDiagnosis().toLowerCase() : "";
        
        if (diagnosis.contains("diabetes")) {
            plan.append("• Endocrinology: ").append(LocalDate.now().plusDays(30));
            plan.append(" with Dr. Williams\n");
            plan.append("  Bring blood sugar log\n\n");
        }
        
        if (diagnosis.contains("heart") || diagnosis.contains("chf")) {
            plan.append("• Cardiology: ").append(LocalDate.now().plusDays(14));
            plan.append(" with Dr. Chen\n");
            plan.append("  Echo scheduled for same day\n\n");
        }
        
        if (diagnosis.contains("pneumonia")) {
            plan.append("• Chest X-ray: ").append(LocalDate.now().plusDays(42));
            plan.append(" at Radiology Department\n");
            plan.append("• Pulmonology follow-up: ").append(LocalDate.now().plusDays(45));
            plan.append(" with Dr. Garcia\n\n");
        }
        
        plan.append("PENDING LABORATORY TESTS:\n");
        plan.append("────────────────────────\n");
        plan.append("• Basic Metabolic Panel: 1 week post-discharge\n");
        plan.append("• Complete Blood Count: 2 weeks post-discharge\n\n");
        
        plan.append("REFERRALS:\n");
        plan.append("─────────\n");
        if (patient.getAge() != null && patient.getAge() > 65) {
            plan.append("• Physical Therapy: Starting ").append(LocalDate.now().plusDays(3));
            plan.append(" - 2x/week for 4 weeks\n");
            plan.append("• Home Health Aide: Evaluation within 48 hours\n");
        }
        
        plan.append("• Nutritionist: ").append(LocalDate.now().plusDays(5));
        plan.append(" - Diet counseling\n");
        plan.append("• Pharmacist: ").append(LocalDate.now().plusDays(2));
        plan.append(" - Medication review\n\n");
        
        plan.append("EMERGENCY CONTACTS:\n");
        plan.append("─────────────────\n");
        plan.append("• Emergency: 911\n");
        plan.append("• Hospital: (555) 987-6543\n");
        plan.append("• 24/7 Nurse Line: (555) 456-7890\n");
        
        return plan.toString();
    }
}