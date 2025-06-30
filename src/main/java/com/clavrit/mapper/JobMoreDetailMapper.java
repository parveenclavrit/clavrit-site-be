package com.clavrit.mapper;

import org.springframework.stereotype.Component;

import com.clavrit.Dto.JobMoreDetailDTO;
import com.clavrit.Entity.JobMoreDetail;
@Component
public class JobMoreDetailMapper {

    public static JobMoreDetailDTO toDTO(JobMoreDetail entity) {
        if (entity == null) return null;

        JobMoreDetailDTO dto = new JobMoreDetailDTO();
        dto.setId(entity.getId());
        dto.setJobResponsibility(entity.getJobResponsibility());
        dto.setJobQualification(entity.getJobQualification());
        dto.setJobCategory(entity.getJobCategory());
        dto.setJobType(entity.getJobType());
        dto.setJobLocation(entity.getJobLocation());
        dto.setIndustry(entity.getIndustry());
        return dto;
    }

    public static JobMoreDetail toEntity(JobMoreDetailDTO dto) {
        if (dto == null) return null;

        JobMoreDetail entity = new JobMoreDetail();
        entity.setId(dto.getId());
        entity.setJobResponsibility(dto.getJobResponsibility());
        entity.setJobQualification(dto.getJobQualification());
        entity.setJobCategory(dto.getJobCategory());
        entity.setJobType(dto.getJobType());
        entity.setJobLocation(dto.getJobLocation());
        entity.setIndustry(dto.getIndustry());
        return entity;
    }
}

