package com.hospital.discharge.service;

import com.hospital.discharge.model.EmailLog;
import com.hospital.discharge.model.Patient;
import com.hospital.discharge.repository.EmailLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private EmailLogRepository emailLogRepository;
    
    @Value("${spring.mail.username}")
    private String fromEmail;

    // Send discharge summary email
    public void sendDischargeSummary(Patient patient, String summary) {
        String subject = "🏥 Your Discharge Summary - " + patient.getName();
        String message = "Dear " + patient.getName() + ",\n\n" +
                        "Your discharge summary is ready.\n\n" +
                        "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                        "PATIENT DETAILS:\n" +
                        "• Name: " + patient.getName() + "\n" +
                        "• Patient ID: " + patient.getPatientId() + "\n" +
                        "• Diagnosis: " + patient.getDiagnosis() + "\n" +
                        "• Discharge Date: " + patient.getDischargeDate() + "\n" +
                        "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n" +
                        "MEDICATIONS:\n" + patient.getMedications() + "\n\n" +
                        "FOLLOW-UP:\n" +
                        "• Please schedule a follow-up appointment within 7 days\n" +
                        "• Contact: (555) 123-4567\n\n" +
                        "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                        "Thank you for choosing City Hospital.\n" +
                        "Wishing you a speedy recovery! 🏥❤️\n" +
                        "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n" +
                        "City Hospital\n" +
                        "contact@cityhospital.com | www.cityhospital.com";
        
        sendEmail(patient, subject, message, "DISCHARGE_SUMMARY");
    }

    // Send payment reminder email
    public void sendPaymentReminder(Patient patient, Double totalAmount, Double dueAmount) {
        String subject = "💰 Payment Reminder - City Hospital";
        String message = "Dear " + patient.getName() + ",\n\n" +
                        "This is a reminder about your pending payment.\n\n" +
                        "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                        "BILLING DETAILS:\n" +
                        "• Total Amount: ₹" + String.format("%.2f", totalAmount) + "\n" +
                        "• Paid Amount: ₹" + String.format("%.2f", (totalAmount - dueAmount)) + "\n" +
                        "• Due Amount: ₹" + String.format("%.2f", dueAmount) + "\n" +
                        "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n" +
                        "Please clear your dues at the earliest.\n\n" +
                        "Payment Options:\n" +
                        "• Online: www.cityhospital.com/pay\n" +
                        "• Bank Transfer: HDFC Bank, A/C: 123456789\n" +
                        "• Cash/ Card at hospital counter\n\n" +
                        "For assistance, call: (555) 987-6543\n\n" +
                        "Thank you,\n" +
                        "City Hospital Billing Department";
        
        sendEmail(patient, subject, message, "PAYMENT_REMINDER");
    }

    // Send appointment reminder email
    public void sendAppointmentReminder(Patient patient, String doctorName, String date, String time) {
        String subject = "📅 Appointment Reminder - City Hospital";
        String message = "Dear " + patient.getName() + ",\n\n" +
                        "This is a reminder for your upcoming appointment.\n\n" +
                        "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                        "APPOINTMENT DETAILS:\n" +
                        "• Doctor: Dr. " + doctorName + "\n" +
                        "• Date: " + date + "\n" +
                        "• Time: " + time + "\n" +
                        "• Location: City Hospital, 2nd Floor\n" +
                        "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n" +
                        "Please arrive 15 minutes early and bring:\n" +
                        "• Previous medical records\n" +
                        "• Insurance card\n" +
                        "• List of current medications\n\n" +
                        "To reschedule, call: (555) 123-4567\n\n" +
                        "Thank you,\n" +
                        "City Hospital Appointments Desk";
        
        sendEmail(patient, subject, message, "APPOINTMENT_REMINDER");
    }

    // Send medicine reminder email
    public void sendMedicineReminder(Patient patient) {
        String subject = "💊 Medicine Reminder - City Hospital";
        String message = "Dear " + patient.getName() + ",\n\n" +
                        "This is a friendly reminder to take your medicines as prescribed.\n\n" +
                        "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                        "YOUR MEDICATIONS:\n" + patient.getMedications() + "\n" +
                        "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n" +
                        "📌 Important Tips:\n" +
                        "• Take medicines at the same time daily\n" +
                        "• Don't skip doses\n" +
                        "• Complete the full course\n" +
                        "• Store medicines properly\n\n" +
                        "If you experience any side effects, contact us immediately:\n" +
                        "• Emergency: 911\n" +
                        "• Doctor: (555) 123-4567\n\n" +
                        "Take care and stay healthy! 🌟\n\n" +
                        "City Hospital Pharmacy";
        
        sendEmail(patient, subject, message, "MEDICINE_REMINDER");
    }

    // Generic email sender
    private void sendEmail(Patient patient, String subject, String content, String type) {
        try {
            // Create email
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(patient.getEmail());
            message.setSubject(subject);
            message.setText(content);
            
            // Send email
            mailSender.send(message);
            
            // Log success
            EmailLog log = new EmailLog(
                patient.getPatientId(),
                patient.getEmail(),
                subject,
                content,
                "SENT"
            );
            emailLogRepository.save(log);
            
            System.out.println("✅ Email sent to: " + patient.getEmail());
            System.out.println("   Subject: " + subject);
            
        } catch (Exception e) {
            // Log failure
            EmailLog log = new EmailLog(
                patient.getPatientId(),
                patient.getEmail(),
                subject,
                content,
                "FAILED: " + e.getMessage()
            );
            emailLogRepository.save(log);
            
            System.err.println("❌ Failed to send email: " + e.getMessage());
        }
    }
}