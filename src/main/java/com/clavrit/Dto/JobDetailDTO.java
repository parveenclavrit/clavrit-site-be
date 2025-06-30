package com.clavrit.Dto;



public class JobDetailDTO {
    private Long id;
    private String jobCategory;
    private String jobDesignation;
    private String jobType;
    private JobMoreDetailDTO moreDetails;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getJobCategory() {
		return jobCategory;
	}
	public void setJobCategory(String jobCategory) {
		this.jobCategory = jobCategory;
	}
	public String getJobDesignation() {
		return jobDesignation;
	}
	public void setJobDesignation(String jobDesignation) {
		this.jobDesignation = jobDesignation;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public JobMoreDetailDTO getMoreDetails() {
		return moreDetails;
	}
	public void setMoreDetails(JobMoreDetailDTO moreDetails) {
		this.moreDetails = moreDetails;
	}

    
}

