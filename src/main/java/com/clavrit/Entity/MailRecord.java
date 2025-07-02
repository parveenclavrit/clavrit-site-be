package com.clavrit.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailRecord {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    
    private String name;
    
    private String subject;
    
    private String message;
    
    private String destination;
    
    private String company;
    
    private String phone;
    
    private String country;
    
    private LocalDateTime sentAt;
    
    private LocalDateTime updatedAt;

}
