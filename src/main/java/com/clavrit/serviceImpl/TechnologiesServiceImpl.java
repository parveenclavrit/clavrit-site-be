package com.clavrit.serviceImpl;

import java.io.File;
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

import com.clavrit.Dto.TechnologiesResponse;
import com.clavrit.Entity.Technologies;
import com.clavrit.Repository.TechnologiesRepository;
import com.clavrit.Service.TechnologiesService;

@Service
public class TechnologiesServiceImpl implements TechnologiesService {
	
	Logger log = LoggerFactory.getLogger(TechnologiesServiceImpl.class);
	
	@Autowired
    private TechnologiesRepository technologiesRepository;
	
	@Value("${file.upload.TechnologyImage}")
    private String techImagePath;

    @Value("${LOCAL_BASE_PATH}")
    private String LOCAL_URL_BASE;

    @Value("${PUBLIC_URL_BASE}")
    private String PUBLIC_URL_BASE;

    @Override
    public TechnologiesResponse save(MultipartFile file) {
        try {
            String imageUrl = saveImage(file);

            Technologies tech = new Technologies();
            tech.setImageUrl(imageUrl);

            Technologies saved = technologiesRepository.save(tech);
            return toDto(saved);

        } catch (Exception e) {
            log.error("Error while saving technology image: {}", e.getMessage());
            throw new RuntimeException("Failed to save technology image: " + e.getMessage());
        }
    }

    @Override
    public TechnologiesResponse update(Long id, MultipartFile file) {
        try {
            Optional<Technologies> optional = technologiesRepository.findById(id);
            if (!optional.isPresent()) {
                throw new RuntimeException("Technology not found with ID: " + id);
            }

            Technologies tech = optional.get();

            // Delete old image if exists
            if (tech.getImageUrl() != null) {
                try {
                    String localPath = tech.getImageUrl().replace(PUBLIC_URL_BASE, LOCAL_URL_BASE);
                    File oldFile = new File(localPath);
                    if (oldFile.exists()) {
                        oldFile.delete();
                        log.info("Deleted old image: {}", localPath);
                    }
                } catch (Exception e) {
                    log.warn("Failed to delete old image: {}", e.getMessage());
                }
            }

            String imageUrl = saveImage(file);
            tech.setImageUrl(imageUrl);

            Technologies updated = technologiesRepository.save(tech);
            return toDto(updated);

        } catch (Exception e) {
            log.error("Error updating technology with ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to update technology: " + e.getMessage());
        }
    }

    @Override
    public String delete(Long id) {
        try {
            Optional<Technologies> optional = technologiesRepository.findById(id);
            if (!optional.isPresent()) {
                return "Technology not found with ID: " + id;
            }

            // Delete associated image
            Technologies tech = optional.get();
            if (tech.getImageUrl() != null) {
                try {
                    String localPath = tech.getImageUrl().replace(PUBLIC_URL_BASE, LOCAL_URL_BASE);
                    File oldFile = new File(localPath);
                    if (oldFile.exists()) {
                        oldFile.delete();
                        log.info("Deleted technology image: {}", localPath);
                    }
                } catch (Exception e) {
                    log.warn("Image deletion failed: {}", e.getMessage());
                }
            }

            technologiesRepository.delete(tech);
            log.info("Technology with ID {} deleted successfully.", id);
            return "Technology with ID " + id + " deleted successfully.";

        } catch (Exception e) {
            log.error("Error deleting technology with ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to delete technology: " + e.getMessage());
        }
    }

    @Override
    public TechnologiesResponse getById(Long id) {
        try {
        	Optional<Technologies> optionalTech = technologiesRepository.findById(id);
        	if (!optionalTech.isPresent()) {
        	    throw new RuntimeException("Technology not found with ID: " + id);
        	}
        	Technologies tech = optionalTech.get();

            return toDto(tech);
        } catch (Exception e) {
            log.error("Error fetching technology with ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to fetch technology: " + e.getMessage());
        }
    }

    @Override
    public List<TechnologiesResponse> getAll() {
        try {
            List<Technologies> techList = technologiesRepository.findAll();
            List<TechnologiesResponse> responseList = new ArrayList<>();
            for (Technologies tech : techList) {
                responseList.add(toDto(tech));
            }
            return responseList;
        } catch (Exception e) {
            log.error("Error fetching all technologies: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch technologies: " + e.getMessage());
        }
    }

    private String saveImage(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;

        try {
            File dir = new File(techImagePath);
            if (!dir.exists()) dir.mkdirs();

            String uniqueName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            String fullPath = techImagePath + uniqueName;

            File dest = new File(fullPath);
            file.transferTo(dest);

            log.info("Saved technology image to: {}", fullPath);

            return fullPath.replace(LOCAL_URL_BASE, PUBLIC_URL_BASE).replace("\\", "/");
        } catch (Exception e) {
            log.error("Error saving image: {}", e.getMessage());
            throw new RuntimeException("Image saving failed: " + e.getMessage());
        }
    }

    private TechnologiesResponse toDto(Technologies tech) {
    	if (tech == null) {
            return null;
        }

        TechnologiesResponse response = new TechnologiesResponse();

        response.setId(tech.getId());
        response.setImageUrl(tech.getImageUrl());

        return response;
    }

}
