package com.clavrit.Dto;

import org.springframework.web.multipart.MultipartFile;

public class JobApplicationRequestDto {

	private String fullName;
    private String email;
    private String phone;
    private String jobAppliedFor;
    private String qualification;
    private String totalYOE;
    private String relevantExperience;
    private String currentCompany;
    private String currentCTC;
    private String noticePeriod;
    private String coverLetter;

    private MultipartFile uploadResume;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getJobAppliedFor() {
		return jobAppliedFor;
	}

	public void setJobAppliedFor(String jobAppliedFor) {
		this.jobAppliedFor = jobAppliedFor;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getTotalYOE() {
		return totalYOE;
	}

	public void setTotalYOE(String totalYOE) {
		this.totalYOE = totalYOE;
	}

	public String getRelevantExperience() {
		return relevantExperience;
	}

	public void setRelevantExperience(String relevantExperience) {
		this.relevantExperience = relevantExperience;
	}

	public String getCurrentCompany() {
		return currentCompany;
	}

	public void setCurrentCompany(String currentCompany) {
		this.currentCompany = currentCompany;
	}

	public String getCurrentCTC() {
		return currentCTC;
	}

	public void setCurrentCTC(String currentCTC) {
		this.currentCTC = currentCTC;
	}

	public String getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public String getCoverLetter() {
		return coverLetter;
	}

	public void setCoverLetter(String coverLetter) {
		this.coverLetter = coverLetter;
	}

	public MultipartFile getUploadResume() {
		return uploadResume;
	}

	public void setUploadResume(MultipartFile uploadResume) {
		this.uploadResume = uploadResume;
	}

	public JobApplicationRequestDto(String fullName, String email, String phone, String jobAppliedFor,
			String qualification, String totalYOE, String relevantExperience, String currentCompany, String currentCTC,
			String noticePeriod, String coverLetter, MultipartFile uploadResume) {
		super();
		this.fullName = fullName;
		this.email = email;
		this.phone = phone;
		this.jobAppliedFor = jobAppliedFor;
		this.qualification = qualification;
		this.totalYOE = totalYOE;
		this.relevantExperience = relevantExperience;
		this.currentCompany = currentCompany;
		this.currentCTC = currentCTC;
		this.noticePeriod = noticePeriod;
		this.coverLetter = coverLetter;
		this.uploadResume = uploadResume;
	}

	public JobApplicationRequestDto() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
    
}

