package com.clavrit.mapper;

import org.springframework.stereotype.Component;

import com.clavrit.Dto.JobDetailDTO;
import com.clavrit.Entity.JobDetail;
@Component
public class JobDetailMapper {
	
	public JobDetailDTO toDTO(JobDetail entity) {
        if (entity == null) return null;

        JobDetailDTO dto = new JobDetailDTO();
        dto.setId(entity.getId());
        dto.setJobDesignation(entity.getJobDesignation());
        dto.setJobResponsibility(entity.getJobResponsibility());
        dto.setJobQualification(entity.getJobQualification());
        dto.setCompetencies(entity.getCompetencies());
        dto.setJobCategory(entity.getJobCategory());
        dto.setJobType(entity.getJobType());
        dto.setJobLocation(entity.getJobLocation());
        dto.setIndustry(entity.getIndustry());
        dto.setCreateAt(entity.getCreateAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    public JobDetail toEntity(JobDetailDTO dto) {
        if (dto == null) return null;

        JobDetail entity = new JobDetail();
        entity.setId(dto.getId());
        entity.setJobDesignation(dto.getJobDesignation());
        entity.setJobResponsibility(dto.getJobResponsibility());
        entity.setJobQualification(dto.getJobQualification());
        entity.setCompetencies(dto.getCompetencies());
        entity.setJobCategory(dto.getJobCategory());
        entity.setJobType(dto.getJobType());
        entity.setJobLocation(dto.getJobLocation());
        entity.setIndustry(dto.getIndustry());
        entity.setCreateAt(dto.getCreateAt());
        entity.setUpdatedAt(dto.getUpdatedAt());

        return entity;
    }
}
