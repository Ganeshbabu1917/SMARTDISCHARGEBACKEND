package com.hospital.discharge.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "sender_id")
    private Long senderId;
    
    @Column(name = "sender_name")
    private String senderName;
    
    @Column(name = "sender_role")
    private String senderRole; // 'patient' or 'admin'
    
    @Column(name = "receiver_id")
    private Long receiverId;
    
    @Column(name = "message", columnDefinition = "TEXT")
    private String message;
    
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    
    @Column(name = "is_read")
    private boolean isRead = false;
    
    @Column(name = "patient_id")
    private String patientId; // For grouping messages by patient
    
    // Constructors
    public Message() {
        this.timestamp = LocalDateTime.now();
        this.isRead = false;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getSenderId() { return senderId; }
    public void setSenderId(Long senderId) { this.senderId = senderId; }
    
    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }
    
    public String getSenderRole() { return senderRole; }
    public void setSenderRole(String senderRole) { this.senderRole = senderRole; }
    
    public Long getReceiverId() { return receiverId; }
    public void setReceiverId(Long receiverId) { this.receiverId = receiverId; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }
    
    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }
}