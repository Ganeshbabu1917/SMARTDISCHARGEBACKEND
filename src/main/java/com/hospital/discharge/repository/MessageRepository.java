package com.hospital.discharge.repository;

import com.hospital.discharge.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    
    // Get all messages between admin and specific patient
    List<Message> findByPatientIdOrderByTimestampAsc(String patientId);
    
    // Get unread messages for admin
    @Query("SELECT m FROM Message m WHERE m.receiverId = :adminId AND m.isRead = false")
    List<Message> findUnreadMessagesForAdmin(@Param("adminId") Long adminId);
    
    // Get all conversations (distinct patients)
    @Query("SELECT DISTINCT m.patientId FROM Message m ORDER BY m.timestamp DESC")
    List<String> findDistinctPatientConversations();
    
    // Get last message from each patient
    @Query("SELECT m FROM Message m WHERE m.timestamp IN (SELECT MAX(m2.timestamp) FROM Message m2 GROUP BY m2.patientId)")
    List<Message> findLastMessagesByPatient();
}