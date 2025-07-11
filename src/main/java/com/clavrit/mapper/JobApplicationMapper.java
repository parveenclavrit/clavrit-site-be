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
	        entity.setJobAppliedFor(dto.getJobAppliedFor());
	        entity.setQualification(dto.getQualification());
	        entity.setTotalYOE(dto.getTotalYOE());
	        entity.setRelevantExperience(dto.getRelevantExperience());
	        entity.setCurrentCompany(dto.getCurrentCompany());
	        entity.setCurrentCTC(dto.getCurrentCTC());
	        entity.setNoticePeriod(dto.getNoticePeriod());
	        entity.setCoverLetter(dto.getCoverLetter());

	        return entity;
	    }
	}


