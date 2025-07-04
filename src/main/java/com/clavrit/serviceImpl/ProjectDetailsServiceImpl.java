package com.clavrit.serviceImpl;

import com.clavrit.Dto.ProjectDto;
import com.clavrit.Entity.Project;
import com.clavrit.Repository.ProjectRepository;
import com.clavrit.Service.ProjectDetailsService;
import com.clavrit.mapper.ProjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectDetailsServiceImpl implements ProjectDetailsService {

	@Autowired
    private ProjectRepository projectRepository;
	
	@Autowired
	private ProjectMapper projectMapper;

    @Value("${file.upload.ProjectImage}")
    private String basePath;

    @Override
    public ProjectDto createProjectDetails(ProjectDto projectDto, List<MultipartFile> images) {
        try {
            Project project = projectMapper.toEntity(projectDto);
            if (images != null && !images.isEmpty()) {
                List<String> imageUrls = saveUploadedFiles(images);
                project.setImageUrl(imageUrls);
            }
            project.setCreatedAt(LocalDateTime.now());
            project.setUpdatedAt(LocalDateTime.now());
            Project saved = projectRepository.save(project);
            log.info("Project created successfully with ID: {}", saved.getId());
            return projectMapper.toDto(saved);
        } catch (Exception e) {
            log.error("Error while creating project: {}", e.getMessage());
            throw new RuntimeException("Failed to create project: " + e.getMessage());
        }
    }

    @Override
    public ProjectDto getProjectDetailsById(Long id) {
        try {
        	Optional<Project> optionalProject = projectRepository.findById(id);
        	if (!optionalProject.isPresent()) {
        	    throw new RuntimeException("Project not found with ID: " + id);
        	}
        	Project project = optionalProject.get();

            log.info("Project fetched with ID: {}", id);
            return projectMapper.toDto(project);
        } catch (Exception e) {
            log.error("Error while fetching project: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch project: " + e.getMessage());
        }
    }

    @Override
    public List<ProjectDto> getAllProjectsDetails() {
        try {
            List<Project> projects = projectRepository.findAll();
            log.info("Total {} projects fetched", projects.size());
            List<ProjectDto> projectDtos = new ArrayList<>();
            for (Project project : projects) {
                projectDtos.add(projectMapper.toDto(project));
            }
            return projectDtos;
        } catch (Exception e) {
            log.error("Error while fetching all projects: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch projects", e);
        }
    }

    @Override
    public ProjectDto updateProjectDetails(Long id, ProjectDto dto, List<MultipartFile> images) {
        try {
        	Optional<Project> optionalProject = projectRepository.findById(id);
        	if (!optionalProject.isPresent()) {
        	    throw new RuntimeException("Project not found with ID: " + id);
        	}
        	Project existing = optionalProject.get();


            if (dto.getTitle() != null) existing.setTitle(dto.getTitle());
            if (dto.getSummary() != null) existing.setSummary(dto.getSummary());
            if (dto.getTechnologies() != null) existing.setTechnologies(dto.getTechnologies());
            if (dto.getKeyPoints() != null) existing.setKeyPoints(dto.getKeyPoints());

            existing.setUpdatedAt(LocalDateTime.now());

            if (images != null && !images.isEmpty()) {
                List<String> oldImages = existing.getImageUrl();
                if (oldImages != null && !oldImages.isEmpty()) {
                    for (String imagePath : oldImages) {
                        File oldFile = new File(imagePath);
                        if (oldFile.exists()) {
                            boolean deleted = oldFile.delete();
                            log.info("Old image deleted: {} -> {}", imagePath, deleted);
                        }
                    }
                }
                List<String> newImages = saveUploadedFiles(images);
                existing.setImageUrl(newImages);
            }

            Project updated = projectRepository.save(existing);
            log.info("Project updated successfully with ID: {}", id);
            return projectMapper.toDto(updated);
        } catch (Exception e) {
            log.error("Error while updating project: {}", e.getMessage());
            throw new RuntimeException("Failed to update project: " + e.getMessage());
        }
    }

    @Override
    public void deleteProjectDetails(Long id) {
        try {
        	Optional<Project> optionalProject = projectRepository.findById(id);
        	if (!optionalProject.isPresent()) {
        	    throw new RuntimeException("Project not found with ID: " + id);
        	}
        	Project project = optionalProject.get();

            projectRepository.delete(project);
            log.info("Project deleted successfully with ID: {}", id);
        } catch (Exception e) {
            log.error("Error while deleting project: {}", e.getMessage());
            throw new RuntimeException("Failed to delete project: " + e.getMessage());
        }
    }

    private List<String> saveUploadedFiles(List<MultipartFile> files) throws Exception {
        List<String> imageUrls = new ArrayList<>();
        File dir = new File(basePath);
        if (!dir.exists()) dir.mkdirs();

        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                String fullPath = basePath + uniqueFileName;
                file.transferTo(new File(fullPath));
                log.info("Saved image to: {}", fullPath);
                imageUrls.add(fullPath);
            }
        }
        return imageUrls;
    }

}