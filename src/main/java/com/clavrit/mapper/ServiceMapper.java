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
        entity.setTitle(dto.getTitle());
        entity.setImg(dto.getImg());
        entity.setSubheading(dto.getSubheading());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setImageUrls(dto.getImageUrls() != null ? new ArrayList<>(dto.getImageUrls()) : new ArrayList<>());

        return entity;
    }

    public ServiceDto toDto(ClavritService entity) {
        if (entity == null) return null;

        ServiceDto dto = new ServiceDto();
        dto.setTitle(entity.getTitle());
        dto.setImg(entity.getImg());
        dto.setSubheading(entity.getSubheading());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setImageUrls(entity.getImageUrls() != null ? new ArrayList<>(entity.getImageUrls()) : new ArrayList<>());

        return dto;
    }
}
