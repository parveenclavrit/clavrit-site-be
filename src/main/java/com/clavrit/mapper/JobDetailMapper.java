package com.clavrit.mapper;

import org.springframework.stereotype.Component;

import com.clavrit.Dto.JobDetailDTO;
import com.clavrit.Entity.JobDetail;
@Component
public class JobDetailMapper {

    public static JobDetailDTO toDTO(JobDetail entity) {
        if (entity == null) return null;

        JobDetailDTO dto = new JobDetailDTO();
        dto.setId(entity.getId());
        dto.setJobCategory(entity.getJobCategory());
        dto.setJobDesignation(entity.getJobDesignation());
        dto.setJobType(entity.getJobType());
        dto.setMoreDetails(JobMoreDetailMapper.toDTO(entity.getMoreDetails()));
        return dto;
    }
}
