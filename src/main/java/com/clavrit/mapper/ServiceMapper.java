package com.clavrit.mapper;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.clavrit.Dto.ServiceDto;
import com.clavrit.Entity.ClavritService;

@Component
public class ServiceMapper {

    public ClavritService toEntity(ServiceDto dto) {
        if (dto == null) return null;

        ClavritService entity = new ClavritService();
        entity.setName(dto.getName());
        entity.setType(dto.getType());
        entity.setDescription(dto.getDescription());
        entity.setImageUrl(dto.getImageUrl() != null ? new ArrayList<>(dto.getImageUrl()) : new ArrayList<>());

        return entity;
    }

    public ServiceDto toDto(ClavritService entity) {
        if (entity == null) return null;

        ServiceDto dto = new ServiceDto();
        dto.setName(entity.getName());
        dto.setType(entity.getType());
        dto.setDescription(entity.getDescription());
        dto.setImageUrl(entity.getImageUrl() != null ? new ArrayList<>(entity.getImageUrl()) : new ArrayList<>());

        return dto;
    }
}

