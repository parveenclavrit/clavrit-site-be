package com.clavrit.Dto;

import org.springframework.web.multipart.MultipartFile;

public class JobApplicationRequestDto {

    private String fullName;
    private String email;
    private String phone;
    private String coverLetter;
    private MultipartFile uploadResume;

    public JobApplicationRequestDto() {
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
}

