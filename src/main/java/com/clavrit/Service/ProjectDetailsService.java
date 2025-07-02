package com.clavrit.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.clavrit.Dto.ProjectDto;

public interface ProjectDetailsService {
	
	ProjectDto createProjectDetails(ProjectDto projectDto,List<MultipartFile> images);

    ProjectDto getProjectDetailsById(Long id);

    List<ProjectDto> getAllProjectsDetails();

    ProjectDto updateProjectDetails(Long id, ProjectDto projectDto,List<MultipartFile> images);

    void deleteProjectDetails(Long id);
    
}
