package com.clavrit.serviceImpl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
import com.clavrit.mapper.ProjectMapper;

@Service
public class ProjectDetailsServiceImpl implements ProjectDetailsService {
	
	Logger log= LoggerFactory.getLogger(ProjectDetailsServiceImpl.class);

	@Autowired
    private ProjectRepository projectRepository;
	
	@Autowired
	private ProjectMapper projectMapper;

    @Value("${file.upload.ProjectImage}")
    private String basePath;
    
    @Value("${LOCAL_BASE_PATH}")
    private String LOCAL_BASE_PATH;
    
    @Value("${PUBLIC_URL_BASE}")
    private String PUBLIC_URL_BASE;

    @Override
    public ProjectDto createProjectDetails(ProjectDto projectDto, List<MultipartFile> images) {
        try {
        	
        	boolean exists = projectRepository.existsByTitleIgnoreCase(projectDto.getTitle());
            if (exists) {
                log.warn("Duplicate project detected with title: {}", projectDto.getTitle());
                throw new RuntimeException("Project already exists with the same title.");
            }
            
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
                    for (String imageUrl : oldImages) {
                        try {
                            // Convert public URL to local file path
                            String localPath = imageUrl.replace(PUBLIC_URL_BASE, LOCAL_BASE_PATH);
                            File oldFile = new File(localPath);
                            if (oldFile.exists()) {
                                boolean deleted = oldFile.delete();
                                log.info("Old image deleted: {} -> {}", localPath, deleted);
                            }
                        } catch (Exception ex) {
                            log.warn("Failed to delete image: {}", imageUrl, ex);
                            throw new RuntimeException("Failed to update project: " + ex.getMessage());
                        }
                    }
                }

                List<String> newImages = saveUploadedFiles(images); // returns public URLs
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

                // Convert local path to public URL
                String publicUrl = fullPath.replace(LOCAL_BASE_PATH, PUBLIC_URL_BASE).replace("\\", "/");
                imageUrls.add(publicUrl);
            }
        }
        return imageUrls;
    }
    @Override
    public List<Project> saveAllProjects(List<Project> projects) {
        try {
        	if (projects == null || projects.isEmpty()) return new ArrayList<>();

            // Fetch all existing projects
            List<Project> existingProjects = projectRepository.findAll();
            Map<String, Project> existingMap = new HashMap<>();
            for (Project existing : existingProjects) {
                existingMap.put(existing.getTitle().toLowerCase().trim(), existing);
            }

            Set<String> seenTitles = new HashSet<>();
            List<Project> toSave = new ArrayList<>();

            for (Project incoming : projects) {
                if (incoming.getTitle() == null) continue;

                String titleKey = incoming.getTitle().toLowerCase().trim();

                
                if (seenTitles.contains(titleKey)) {
                    log.warn("Duplicate in input list skipped: {}", incoming.getTitle());
                    continue;
                }
                seenTitles.add(titleKey);

                if (existingMap.containsKey(titleKey)) {
                    Project existing = existingMap.get(titleKey);

                    existing.setSummary(incoming.getSummary());
                    existing.setTechnologies(incoming.getTechnologies());
                    existing.setKeyPoints(incoming.getKeyPoints());
                    if (existing.getImageUrl() != null && !existing.getImageUrl().isEmpty()) {
                        for (String imageUrl : existing.getImageUrl()) {
                            try {
                                // Convert public URL to local file path
                                String localPath = imageUrl.replace(PUBLIC_URL_BASE, LOCAL_BASE_PATH);
                                File oldFile = new File(localPath);
                                if (oldFile.exists()) {
                                    boolean deleted = oldFile.delete();
                                    log.info("Old image deleted: {} -> {}", localPath, deleted);
                                }
                            } catch (Exception ex) {
                                log.warn("Failed to delete image: {}", imageUrl, ex);
                                throw new RuntimeException("Failed to update project: " + ex.getMessage());
                            }
                        }
                    existing.setImageUrl(incoming.getImageUrl());
                    }

                    existing.setUpdatedAt(LocalDateTime.now());

                    toSave.add(existing);
                    log.info("Project updated: {}", existing.getTitle());
                } else {
                    incoming.setCreatedAt(LocalDateTime.now());
                    incoming.setUpdatedAt(LocalDateTime.now());
                    toSave.add(incoming);
                    log.info("New project added: {}", incoming.getTitle());
                }
            }

            List<Project> saved = projectRepository.saveAll(toSave);
            log.info("{} projects (new + updated) saved successfully.", saved.size());
            return saved;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save projects: " + e.getMessage());
        }
    }


}