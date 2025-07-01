package com.clavrit.Entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKeyColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    
    private String summary;
    
    @ElementCollection
    @CollectionTable(name = "project_images")
    @Column(name = "image_url")
    private List<String> imageUrl;

    @ElementCollection
    private List<String> technologies;

    @ElementCollection
    @MapKeyColumn(name = "key_heading")
    @Column(name = "explanation")
    @CollectionTable(name = "project_keypoints")
    private Map<String, String> keyPoints;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;

}
