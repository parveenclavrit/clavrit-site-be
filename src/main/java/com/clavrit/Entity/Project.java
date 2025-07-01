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

@Entity
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

	public Project(String title, String summary, List<String> imageUrl, List<String> technologies,
			Map<String, String> keyPoints) {
		super();
		this.title = title;
		this.summary = summary;
		this.imageUrl = imageUrl;
		this.technologies = technologies;
		this.keyPoints = keyPoints;
	}

	public Project() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List<String> getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(List<String> imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<String> getTechnologies() {
		return technologies;
	}

	public void setTechnologies(List<String> technologies) {
		this.technologies = technologies;
	}

	public Map<String, String> getKeyPoints() {
		return keyPoints;
	}

	public void setKeyPoints(Map<String, String> keyPoints) {
		this.keyPoints = keyPoints;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
    
    

}
