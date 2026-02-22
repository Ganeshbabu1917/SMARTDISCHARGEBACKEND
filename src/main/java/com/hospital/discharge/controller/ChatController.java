package com.hospital.discharge.controller;

import com.hospital.discharge.model.Message;
import com.hospital.discharge.model.User;
import com.hospital.discharge.model.Admin;
import com.hospital.discharge.repository.MessageRepository;
import com.hospital.discharge.repository.UserRepository;
import com.hospital.discharge.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://ec2-13-203-67-191.ap-south-1.compute.amazonaws.com:3000")
public class ChatController {

    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AdminRepository adminRepository;

    // Send message from patient to admin
    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody MessageRequest request) {
        try {
            System.out.println("📝 New message from: " + request.getSenderId());
            
            Message message = new Message();
            message.setSenderId(request.getSenderId());
            message.setSenderName(request.getSenderName());
            message.setSenderRole(request.getSenderRole());
            message.setReceiverId(request.getReceiverId());
            message.setMessage(request.getMessage());
            message.setPatientId(request.getPatientId());
            message.setTimestamp(LocalDateTime.now());
            message.setRead(false);
            
            Message saved = messageRepository.save(message);
            System.out.println("✅ Message saved with ID: " + saved.getId());
            
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
            
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get messages for a patient
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<?> getPatientMessages(@PathVariable String patientId) {
        try {
            List<Message> messages = messageRepository.findByPatientIdOrderByTimestampAsc(patientId);
            return new ResponseEntity<>(messages, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all conversations for admin
    @GetMapping("/admin/conversations")
    public ResponseEntity<?> getAdminConversations() {
        try {
            List<Message> conversations = messageRepository.findLastMessagesByPatient();
            return new ResponseEntity<>(conversations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get conversation between admin and specific patient
    @GetMapping("/admin/patient/{patientId}")
    public ResponseEntity<?> getConversationWithPatient(@PathVariable String patientId) {
        try {
            List<Message> messages = messageRepository.findByPatientIdOrderByTimestampAsc(patientId);
            return new ResponseEntity<>(messages, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Mark messages as read
    @PutMapping("/read/{messageId}")
    public ResponseEntity<?> markAsRead(@PathVariable Long messageId) {
        try {
            Optional<Message> messageOpt = messageRepository.findById(messageId);
            if (messageOpt.isPresent()) {
                Message message = messageOpt.get();
                message.setRead(true);
                messageRepository.save(message);
                return new ResponseEntity<>("Message marked as read", HttpStatus.OK);
            }
            return new ResponseEntity<>("Message not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get unread count for admin
    @GetMapping("/admin/unread/{adminId}")
    public ResponseEntity<?> getUnreadCount(@PathVariable Long adminId) {
        try {
            List<Message> unread = messageRepository.findUnreadMessagesForAdmin(adminId);
            return new ResponseEntity<>(unread.size(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DTO
    static class MessageRequest {
        private Long senderId;
        private String senderName;
        private String senderRole;
        private Long receiverId;
        private String message;
        private String patientId;
        
        // Getters and Setters
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
        
        public String getPatientId() { return patientId; }
        public void setPatientId(String patientId) { this.patientId = patientId; }
    }
}
