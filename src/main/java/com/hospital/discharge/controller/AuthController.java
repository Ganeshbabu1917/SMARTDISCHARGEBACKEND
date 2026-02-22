package com.hospital.discharge.controller;

import com.hospital.discharge.model.User;
import com.hospital.discharge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://ec2-13-126-142-30.ap-south-1.compute.amazonaws.com:3000")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // SIGN UP with ROLE
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {
            System.out.println("📝 Signup request: " + request.getEmail() + " as " + request.getRole());
            
            // Check if email already exists
            Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
            
            if (existingUser.isPresent()) {
                return new ResponseEntity<>(
                    new AuthResponse(null, null, request.getEmail(), "Email already registered", false, null),
                    HttpStatus.BAD_REQUEST
                );
            }
            
            // Create user account with role
            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setUsername(request.getEmail()); // Use email as username
            user.setRole(request.getRole() != null ? request.getRole().toLowerCase() : "patient");
            user.setCreatedAt(LocalDateTime.now());
            
            User savedUser = userRepository.save(user);
            System.out.println("✅ " + request.getRole() + " account created: " + savedUser.getEmail() + " with role: " + savedUser.getRole());
            
            return new ResponseEntity<>(
                new AuthResponse(
                    savedUser.getId().toString(),
                    savedUser.getName(),
                    savedUser.getEmail(),
                    request.getRole() + " account created successfully",
                    true,
                    savedUser.getRole()
                ),
                HttpStatus.CREATED
            );
            
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(
                new AuthResponse(null, null, null, "Error: " + e.getMessage(), false, null),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    // LOGIN with role detection
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            System.out.println("📝 Login attempt: " + request.getEmail());
            
            // Check in users table only
            Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
            
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                if (user.getPassword().equals(request.getPassword())) {
                    user.setLastLogin(LocalDateTime.now());
                    userRepository.save(user);
                    
                    String role = user.getRole() != null ? user.getRole().toLowerCase() : "patient";
                    System.out.println("✅ Login successful for: " + user.getEmail() + " with role: " + role);
                    
                    return new ResponseEntity<>(
                        new AuthResponse(
                            user.getId().toString(),
                            user.getName(),
                            user.getEmail(),
                            "Login successful",
                            true,
                            role
                        ),
                        HttpStatus.OK
                    );
                }
            }
            
            return new ResponseEntity<>(
                new AuthResponse(null, null, null, "Invalid email or password", false, null),
                HttpStatus.UNAUTHORIZED
            );
            
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(
                new AuthResponse(null, null, null, "Error: " + e.getMessage(), false, null),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
    
    // DTO Classes
    static class SignupRequest {
        private String name;
        private String email;
        private String password;
        private String role;
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }
    
    static class LoginRequest {
        private String email;
        private String password;
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
    
    static class AuthResponse {
        private String id;
        private String name;
        private String email;
        private String message;
        private boolean success;
        private String role;
        
        public AuthResponse(String id, String name, String email, String message, boolean success, String role) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.message = message;
            this.success = success;
            this.role = role;
        }
        
        public String getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getMessage() { return message; }
        public boolean isSuccess() { return success; }
        public String getRole() { return role; }
    }
}
