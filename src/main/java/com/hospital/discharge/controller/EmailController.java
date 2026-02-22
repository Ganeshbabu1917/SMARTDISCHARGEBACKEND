package com.hospital.discharge.controller;

import com.hospital.discharge.model.Patient;
import com.hospital.discharge.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "http://ec2-13-126-142-30.ap-south-1.compute.amazonaws.com:3000")
public class EmailController {

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Value("${spring.mail.username}")
    private String fromEmail;

    // ========== TEST ENDPOINTS ==========
    @GetMapping("/test")
    public String test() {
        return "✅ Email API is working!";
    }

    // ========== 1. DISCHARGE SUMMARY ==========
    @PostMapping("/send-summary/{patientId}")
    public String sendSummary(@PathVariable String patientId) {
        try {
            Optional<Patient> patientOpt = patientRepository.findByPatientId(patientId);
            if (patientOpt.isEmpty()) {
                return "❌ Patient not found";
            }
            
            Patient patient = patientOpt.get();
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(patient.getEmail());
            message.setSubject("🏥 Your Discharge Summary - " + patient.getName());
            message.setText("Hello " + patient.getName() + ",\n\n" +
                           "Your discharge summary is ready.\n\n" +
                           "Patient ID: " + patient.getPatientId() + "\n" +
                           "Diagnosis: " + patient.getDiagnosis() + "\n" +
                           "Medications: " + patient.getMedications() + "\n\n" +
                           "Please follow up within 7 days.\n\n" +
                           "Thank you,\nCity Hospital");
            
            mailSender.send(message);
            return "✅ Discharge summary sent to " + patient.getEmail();
            
        } catch (Exception e) {
            return "❌ Error: " + e.getMessage();
        }
    }

    // ========== 2. PAYMENT REMINDER ==========
    @PostMapping("/send-payment/{patientId}")
    public String sendPaymentReminder(@PathVariable String patientId) {
        try {
            Optional<Patient> patientOpt = patientRepository.findByPatientId(patientId);
            if (patientOpt.isEmpty()) {
                return "❌ Patient not found";
            }
            
            Patient patient = patientOpt.get();
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(patient.getEmail());
            message.setSubject("💰 Payment Reminder - City Hospital");
            message.setText("Hello " + patient.getName() + ",\n\n" +
                           "This is a reminder about your pending payment.\n\n" +
                           "Please clear your dues at the earliest.\n\n" +
                           "Payment Options:\n" +
                           "• Online: www.cityhospital.com/pay\n" +
                           "• Cash at counter\n\n" +
                           "Thank you,\nCity Hospital Billing Department");
            
            mailSender.send(message);
            return "✅ Payment reminder sent to " + patient.getEmail();
            
        } catch (Exception e) {
            return "❌ Error: " + e.getMessage();
        }
    }

    // ========== 3. APPOINTMENT REMINDER ==========
    @PostMapping("/send-appointment/{patientId}")
    public String sendAppointmentReminder(
            @PathVariable String patientId,
            @RequestParam String doctor,
            @RequestParam String date,
            @RequestParam String time) {
        try {
            Optional<Patient> patientOpt = patientRepository.findByPatientId(patientId);
            if (patientOpt.isEmpty()) {
                return "❌ Patient not found";
            }
            
            Patient patient = patientOpt.get();
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(patient.getEmail());
            message.setSubject("📅 Appointment Reminder - City Hospital");
            message.setText("Hello " + patient.getName() + ",\n\n" +
                           "This is a reminder for your upcoming appointment.\n\n" +
                           "Doctor: Dr. " + doctor + "\n" +
                           "Date: " + date + "\n" +
                           "Time: " + time + "\n" +
                           "Location: City Hospital, 2nd Floor\n\n" +
                           "Please arrive 15 minutes early.\n\n" +
                           "Thank you,\nCity Hospital Appointments Desk");
            
            mailSender.send(message);
            return "✅ Appointment reminder sent to " + patient.getEmail();
            
        } catch (Exception e) {
            return "❌ Error: " + e.getMessage();
        }
    }

    // ========== 4. MEDICINE REMINDER ==========
    @PostMapping("/send-medicine/{patientId}")
    public String sendMedicineReminder(@PathVariable String patientId) {
        try {
            Optional<Patient> patientOpt = patientRepository.findByPatientId(patientId);
            if (patientOpt.isEmpty()) {
                return "❌ Patient not found";
            }
            
            Patient patient = patientOpt.get();
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(patient.getEmail());
            message.setSubject("💊 Medicine Reminder - City Hospital");
            message.setText("Hello " + patient.getName() + ",\n\n" +
                           "This is a friendly reminder to take your medicines as prescribed.\n\n" +
                           "Your Medications:\n" + patient.getMedications() + "\n\n" +
                           "• Take at same time daily\n" +
                           "• Don't skip doses\n" +
                           "• Complete the full course\n\n" +
                           "Thank you,\nCity Hospital Pharmacy");
            
            mailSender.send(message);
            return "✅ Medicine reminder sent to " + patient.getEmail();
            
        } catch (Exception e) {
            return "❌ Error: " + e.getMessage();
        }
    }
}
