package com.clavrit.Dto;

import java.time.LocalDateTime;
import java.util.List;

public class BlogDto {
	
	private Long id;

    private String title;

    private String subtitle;

    private String authorName;

    private String summary;

    private String content;

    private String advantages;

    private String disadvantages;

    private String conclusion;

    private List<String> imageUrl;

    private List<String> tags;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

	public BlogDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BlogDto(Long id, String title, String subtitle, String authorName, String summary, String content,
			String advantages, String disadvantages, String conclusion, List<String> imageUrl, List<String> tags,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.title = title;
		this.subtitle = subtitle;
		this.authorName = authorName;
		this.summary = summary;
		this.content = content;
		this.advantages = advantages;
		this.disadvantages = disadvantages;
		this.conclusion = conclusion;
		this.imageUrl = imageUrl;
		this.tags = tags;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
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

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAdvantages() {
		return advantages;
	}

	public void setAdvantages(String advantages) {
		this.advantages = advantages;
	}

	public String getDisadvantages() {
		return disadvantages;
	}

	public void setDisadvantages(String disadvantages) {
		this.disadvantages = disadvantages;
	}

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public List<String> getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(List<String> imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
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
