package com.clavrit.Entity;

import javax.persistence.*;

@Entity
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String phone;

    private String jobAppliedFor;
    private String qualification;
    private String totalYOE; // total years of experience
    private String relevantExperience;
    private String currentCompany;
    private String currentCTC;
    private String noticePeriod;

    @Column(length = 3000)
    private String coverLetter;

    private String resumeFilePath;
    private String resumeFileName;
    private String resumeFileType;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getResumeFilePath() {
		return resumeFilePath;
	}
	public void setResumeFilePath(String resumeFilePath) {
		this.resumeFilePath = resumeFilePath;
	}
	public String getResumeFileName() {
		return resumeFileName;
	}
	public void setResumeFileName(String resumeFileName) {
		this.resumeFileName = resumeFileName;
	}
	public String getResumeFileType() {
		return resumeFileType;
	}
	public void setResumeFileType(String resumeFileType) {
		this.resumeFileType = resumeFileType;
	}

    
}