package com.clavrit.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.clavrit.Dto.PartnerRequestDto;
import com.clavrit.Entity.PartnerRequest;

@Component
public class PartnerRequestMapper {
	public PartnerRequestDto toDto(PartnerRequest entity) {
        if (entity == null) return null;

        PartnerRequestDto dto = new PartnerRequestDto();
        dto.setId(entity.getId());
        dto.setFullName(entity.getFullName());
        dto.setCompanyName(entity.getCompanyName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setMessage(entity.getMessage());
        dto.setSubmittedAt(entity.getSubmittedAt());
        return dto;
    }

    public PartnerRequest toEntity(PartnerRequestDto dto) {
        if (dto == null) return null;

        PartnerRequest entity = new PartnerRequest();
        entity.setFullName(dto.getFullName());
        entity.setCompanyName(dto.getCompanyName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setMessage(dto.getMessage());
        entity.setSubmittedAt(LocalDateTime.now());
        return entity;
    }

}
