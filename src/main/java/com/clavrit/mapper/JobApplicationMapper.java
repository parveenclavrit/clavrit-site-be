package com.clavrit.mapper;


import org.springframework.stereotype.Component;

import com.clavrit.Dto.JobApplicationRequestDto;
import com.clavrit.Entity.JobApplication;

@Component
public class JobApplicationMapper {

    
    public JobApplication toEntity(JobApplicationRequestDto dto) {
        JobApplication entity = new JobApplication();
        entity.setFullName(dto.getFullName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setCoverLetter(dto.getCoverLetter());
        
        if (dto.getUploadResume() != null && !dto.getUploadResume().isEmpty()) {
            try {
                entity.setResumeFile(dto.getUploadResume().getBytes());
                entity.setResumeFileName(dto.getUploadResume().getOriginalFilename());
                entity.setResumeFileType(dto.getUploadResume().getContentType());
            } catch (Exception e) {
                throw new RuntimeException("Failed to convert uploaded file", e);
            }
        }
        return entity;
    }
}

