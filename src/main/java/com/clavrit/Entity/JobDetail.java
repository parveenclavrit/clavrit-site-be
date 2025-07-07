package com.clavrit.Entity;

import javax.persistence.*;
@Entity
@Table(name = "job_details")
public class JobDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobCategory;
    private String jobDesignation;
    private String jobType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "more_detail_id")
    private JobMoreDetail moreDetails;

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

	public JobMoreDetail getMoreDetails() {
		return moreDetails;
	}

	public void setMoreDetails(JobMoreDetail moreDetails) {
		this.moreDetails = moreDetails;
	}

    
}

