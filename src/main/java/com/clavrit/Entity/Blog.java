package com.clavrit.Entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.ElementCollection;
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
public class Blog {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String subtitle;

    private String authorName;

    private String summary;

    private String content;

    private String advantages;

    private String disadvantages;

    private String conclusion;

    @ElementCollection
    private List<String> imageUrl;

    @ElementCollection
    private List<String> tags;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
