package com.clavrit.Entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;
@Entity
@Table(name = "job_details")
public class JobDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobDesignation;
    
    @ElementCollection
    private List<String> jobResponsibility;
    
    @ElementCollection
    private List<String> jobQualification;
    
    @ElementCollection
    private List<String> competencies;
    
    private String jobCategory;
    private String jobType;
    private String jobLocation;
    private String industry;

    private LocalDateTime createAt;
    
    private LocalDateTime updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJobDesignation() {
		return jobDesignation;
	}

	public void setJobDesignation(String jobDesignation) {
		this.jobDesignation = jobDesignation;
	}

	public List<String> getJobResponsibility() {
		return jobResponsibility;
	}

	public void setJobResponsibility(List<String> jobResponsibility) {
		this.jobResponsibility = jobResponsibility;
	}

	public List<String> getJobQualification() {
		return jobQualification;
	}

	public void setJobQualification(List<String> jobQualification) {
		this.jobQualification = jobQualification;
	}

	public List<String> getCompetencies() {
		return competencies;
	}

	public void setCompetencies(List<String> competencies) {
		this.competencies = competencies;
	}

	public String getJobCategory() {
		return jobCategory;
	}

	public void setJobCategory(String jobCategory) {
		this.jobCategory = jobCategory;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getJobLocation() {
		return jobLocation;
	}

	public void setJobLocation(String jobLocation) {
		this.jobLocation = jobLocation;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

    
}

