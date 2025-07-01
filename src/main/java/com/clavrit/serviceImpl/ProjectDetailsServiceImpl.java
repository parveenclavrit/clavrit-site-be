package com.clavrit.serviceImpl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.clavrit.Dto.ProjectDto;
import com.clavrit.Entity.Project;
import com.clavrit.Repository.ProjectRepository;
import com.clavrit.Service.ProjectDetailsService;

@Service
public class ProjectDetailsServiceImpl implements ProjectDetailsService{
	
	Logger logger = LoggerFactory.getLogger(ProjectDetailsServiceImpl.class);
	
	@Autowired
    private ProjectRepository projectRepository;
	
	@Value("${file.upload.ProjectImage}")
	private String basePath;

	@Override
	public ProjectDto createProjectDetails(ProjectDto projectDto,List<MultipartFile> images) {
		try {
            Project project = mapToEntity(projectDto);
            List<String> imagesUrl = new ArrayList<>();
	        
            if (images != null && !images.isEmpty()) {
            	File dir = new File(basePath);
		        if (!dir.exists()) {
		            dir.mkdirs();
		        }
		        for (MultipartFile file : images) {
		            if (file != null && !file.isEmpty()) {
		                String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		                String fullPath = basePath + uniqueFileName;
	
		                File dest = new File(fullPath);
		                file.transferTo(dest);
		                logger.info("Saved image to: {}", fullPath);
	
		                imagesUrl.add(fullPath);
		            }
		        }
            }
	        
	        project.setImageUrl(imagesUrl);
	        project.setCreatedAt(LocalDateTime.now());
	        
            Project saved = projectRepository.save(project);
            logger.info("Project created successfully with ID: {}", saved.getId());
            return mapToDto(saved);
        } catch (Exception e) {
            logger.error("Error while creating project: {}", e.getMessage());
            throw new RuntimeException("Failed to create project"+ e.getMessage());
        }
	}

	@Override
	public ProjectDto getProjectDetailsById(Long id) {
		try {
            Optional<Project> optionalProject = projectRepository.findById(id);
            if (optionalProject.isPresent()) {
                logger.info("Project fetched with ID: {}", id);
                return mapToDto(optionalProject.get());
            } else {
                logger.warn("Project not found with ID: {}", id);
                throw new RuntimeException("Project not found with ID: " + id);
            }
        } catch (Exception e) {
            logger.error("Error while fetching project: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch project"+ e.getMessage());
        }
	}

	@Override
	public List<ProjectDto> getAllProjectsDetails() {
		try {
            List<Project> projects = projectRepository.findAll();
            logger.info("Total {} projects fetched", projects.size());
            
            List<ProjectDto> projectDtos = new ArrayList<>();
            for (Project project : projects) {
                projectDtos.add(mapToDto(project));
            }
            return projectDtos;

        } catch (Exception e) {
            logger.error("Error while fetching all projects: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch projects", e);
        }
	}

	@Override
	public ProjectDto updateProjectDetails(Long id, ProjectDto dto,List<MultipartFile> images) {
		try {
			Optional<Project> optionalProject = projectRepository.findById(id);
			if (!optionalProject.isPresent()) {
			    throw new RuntimeException("Project not found with ID: " + id);
			}
			Project existing = optionalProject.get();

            if (dto.getTitle() != null) {
                existing.setTitle(dto.getTitle());
            }
            if (dto.getSummary() != null) {
                existing.setSummary(dto.getSummary());
            }
            if (dto.getTechnologies() != null) {
                existing.setTechnologies(dto.getTechnologies());
            }
            if (dto.getKeyPoints() != null) {
                existing.setKeyPoints(dto.getKeyPoints());
            }

            existing.setUpdatedAt(LocalDateTime.now());
            

            List<String> existingImages = existing.getImageUrl() != null ? existing.getImageUrl() : new ArrayList<>();
	        
            if (images != null && !images.isEmpty()) {
            	List<String> oldImages = existing.getImageUrl();
		        if (oldImages != null && !oldImages.isEmpty()) {
		            for (String imagePath : oldImages) {
		                File oldFile = new File(imagePath);
		                if (oldFile.exists()) {
		                    boolean deleted = oldFile.delete();
		                    logger.info("Old image deleted: {} -> {}", imagePath, deleted);
		                }
		            }
		        }
		        existingImages=new ArrayList<>();
		        
		        File dir = new File(basePath);
		        if (!dir.exists()) {
		            dir.mkdirs();
		        }
		        for (MultipartFile file : images) {
		            if (file != null && !file.isEmpty()) {
		                String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		                String fullPath = basePath + uniqueFileName;
	
		                File dest = new File(fullPath);
		                file.transferTo(dest);
		                logger.info("Saved image to: {}", fullPath);
	
		                existingImages.add(fullPath);
		            }
		        }
            }
            
	        existing.setImageUrl(existingImages);

            Project updated = projectRepository.save(existing);
            logger.info("Project updated successfully with ID: {}", id);
            return mapToDto(updated);
        } catch (Exception e) {
            logger.error("Error while updating project: {}", e.getMessage());
            throw new RuntimeException("Failed to update project -"+ e.getMessage());
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
            logger.info("Project deleted successfully with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error while deleting project: {}", e.getMessage());
            throw new RuntimeException("Failed to delete project"+ e.getMessage());
        }
	}
	
	
	private ProjectDto mapToDto(Project project) {
        return new ProjectDto(
                project.getId(),
                project.getTitle(),
                project.getSummary(),
                project.getImageUrl(),
                project.getTechnologies(),
                project.getKeyPoints(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
	
	private Project mapToEntity(ProjectDto dto) {
        return new Project(
                dto.getTitle(),
                dto.getSummary(),
                dto.getImageUrl(),
                dto.getTechnologies(),
                dto.getKeyPoints()
        );
    }

}
