package com.clavrit.Dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


public class ProjectDto {
	
	private Long id;
	
    private String title;
    
    private String summary;
    
    private List<String> imageUrl;
    
    private List<String> technologies;
    
    private List<String> keyPoints;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;

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

	public List<String> getKeyPoints() {
		return keyPoints;
	}

	public void setKeyPoints(List<String> keyPoints) {
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
