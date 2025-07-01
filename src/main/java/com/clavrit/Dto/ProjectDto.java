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
    
    private Map<String, String> keyPoints;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;

	public ProjectDto(Long id, String title, String summary, List<String> imageUrl, List<String> technologies,
			Map<String, String> keyPoints, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.title = title;
		this.summary = summary;
		this.imageUrl = imageUrl;
		this.technologies = technologies;
		this.keyPoints = keyPoints;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public ProjectDto() {
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
