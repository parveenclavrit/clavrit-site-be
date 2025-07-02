package com.clavrit.mapper;

import org.springframework.stereotype.Component;

import com.clavrit.Dto.ProjectDto;
import com.clavrit.Entity.Project;

@Component
public class ProjectMapper {

    public ProjectDto toDto(Project project) {
        if (project == null) {
            return null;
        }

        ProjectDto dto = new ProjectDto();
        dto.setId(project.getId());
        dto.setTitle(project.getTitle());
        dto.setSummary(project.getSummary());
        dto.setImageUrl(project.getImageUrl());
        dto.setTechnologies(project.getTechnologies());
        dto.setKeyPoints(project.getKeyPoints());
        dto.setCreatedAt(project.getCreatedAt());
        dto.setUpdatedAt(project.getUpdatedAt());

        return dto;
    }

    public Project toEntity(ProjectDto dto) {
        if (dto == null) {
            return null;
        }

        Project project = new Project();
        project.setId(dto.getId());
        project.setTitle(dto.getTitle());
        project.setSummary(dto.getSummary());
        project.setImageUrl(dto.getImageUrl());
        project.setTechnologies(dto.getTechnologies());
        project.setKeyPoints(dto.getKeyPoints());
        project.setCreatedAt(dto.getCreatedAt());
        project.setUpdatedAt(dto.getUpdatedAt());

        return project;
    }
}
