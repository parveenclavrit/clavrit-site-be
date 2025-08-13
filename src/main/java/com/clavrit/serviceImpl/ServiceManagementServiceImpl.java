package com.clavrit.serviceImpl;

import com.clavrit.Dto.ServiceDto;
import com.clavrit.Entity.Blog;
import com.clavrit.Entity.ClavritService;
import com.clavrit.Repository.ServiceRepository;
import com.clavrit.Service.ServiceManagementService;
import com.clavrit.mapper.ServiceMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class ServiceManagementServiceImpl implements ServiceManagementService {
	
	Logger log=LoggerFactory.getLogger(ServiceManagementServiceImpl.class);

    @Autowired
    private ServiceRepository serviceRepo;

    @Autowired
    private ServiceMapper mapper;

    @Value("${file.upload.service}")
    private String servicePath; 
    
    @Value("${LOCAL_BASE_PATH}")
    private String LOCAL_URL_BASE;
    
    @Value("${PUBLIC_URL_BASE}")
    private String PUBLIC_URL_BASE;

    @Override
    public ClavritService createService(ServiceDto dto, List<MultipartFile> files) {
        try {
            ClavritService entity = mapper.toEntity(dto);

            // Check for existing service by title and description
            Optional<ClavritService> existing = serviceRepo.findByTitleAndDescription(
                entity.getTitle(), entity.getDescription());

            if (existing.isPresent()) {
                throw new RuntimeException("Duplicate service: A service with the same title and description already exists.");
            }

            // Save image files and set URLs
            List<String> imageUrls = saveImages(files);
            entity.setImageUrls(imageUrls); // updated field name

            return serviceRepo.save(entity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create service: " + e.getMessage(), e);
        }
    }

    
    @Override
    public List<ClavritService> createServiceList(List<ClavritService> services) {
        try {
            if (services == null || services.isEmpty()) return Collections.emptyList();

            List<ClavritService> existingServices = serviceRepo.findAll();

            Map<String, ClavritService> existingMap = new HashMap<>();
            for (ClavritService existing : existingServices) {
                String key = (existing.getTitle() + "|" + existing.getDescription()).toLowerCase().trim();
                existingMap.put(key, existing);
            }

            Set<String> processedKeys = new HashSet<>();
            List<ClavritService> servicesToSave = new ArrayList<>();

            for (ClavritService incoming : services) {
                String key = (incoming.getTitle() + "|" + incoming.getDescription()).toLowerCase().trim();

                if (processedKeys.contains(key)) {
                    log.warn("Duplicate service found in input list. Skipping: {}", incoming.getTitle());
                    continue;
                }

                processedKeys.add(key);

                if (existingMap.containsKey(key)) {
                    ClavritService existing = existingMap.get(key);

                    if (incoming.getSlug() != null) existing.setSlug(incoming.getSlug());
                    if (incoming.getMetaTitle() != null) existing.setMetaTitle(incoming.getMetaTitle());
                    if (incoming.getMetaDescription() != null) existing.setMetaDescription(incoming.getMetaDescription());

                    if (incoming.getSubheading() != null) existing.setSubheading(incoming.getSubheading());
                    if (incoming.getContent() != null) existing.setContent(incoming.getContent());
                    if (incoming.getCategory() != null) existing.setCategory(incoming.getCategory()); 
                    if (incoming.getImageUrls() != null && !incoming.getImageUrls().isEmpty()) {
                        existing.setImageUrls(incoming.getImageUrls());
                    }

                    servicesToSave.add(existing);
                } else {
                    servicesToSave.add(incoming);
                }
            }

            List<ClavritService> saved = serviceRepo.saveAll(servicesToSave);
            log.info("Saved {} services (created/updated)", saved.size());
            return saved;

        } catch (Exception e) {
            throw new RuntimeException("Failed to create services: " + e.getMessage(), e);
        }
    }



    @Override
    public List<ClavritService> getAllServices() {
        return serviceRepo.findAll();
    }

    @Override
    public Optional<ClavritService> getService(Long id) {
        return serviceRepo.findById(id);
    }

    @Override
    public ClavritService updateService(Long id, ServiceDto dto, List<MultipartFile> images) {
        try {
            Optional<ClavritService> optionalService = serviceRepo.findById(id);
            if (!optionalService.isPresent()) {
                throw new RuntimeException("Service not found with ID: " + id);
            }

            ClavritService service = optionalService.get();

            // Partial update of fields
            service.setTitle(dto.getTitle() != null ? dto.getTitle() : service.getTitle());
            service.setSubheading(dto.getSubheading() != null ? dto.getSubheading() : service.getSubheading());
            service.setDescription(dto.getDescription() != null ? dto.getDescription() : service.getDescription());
            service.setContent(dto.getContent() != null ? dto.getContent() : service.getContent());
            service.setCategory(dto.getCategory() != null ? dto.getCategory() : service.getCategory());
            service.setSlug(dto.getSlug() != null ? dto.getSlug() : service.getSlug());
            service.setMetaTitle(dto.getMetaTitle() != null ? dto.getMetaTitle() : service.getMetaTitle());
            service.setMetaDescription(dto.getMetaDescription() != null ? dto.getMetaDescription() : service.getMetaDescription());


            // Update images if new ones are provided
            if (images != null && !images.isEmpty()) {
                List<String> existingImages = service.getImageUrls();
                if (existingImages != null && !existingImages.isEmpty()) {
                    for (String url : existingImages) {
                        try {
                            String localPath = url.replace(PUBLIC_URL_BASE, LOCAL_URL_BASE);
                            File file = new File(localPath);
                            if (file.exists()) {
                                file.delete();
                            }
                        } catch (Exception e) {
                            // Log if needed
                        }
                    }
                }

                List<String> imageUrls = saveImages(images);
                service.setImageUrls(imageUrls);
            }

            return serviceRepo.save(service);

        } catch (Exception e) {
            throw new RuntimeException("Failed to update service: " + e.getMessage());
        }
    }


    @Override
    public void deleteService(Long id) {
        serviceRepo.deleteById(id);
    }
    
    @Override
    public List<ClavritService> getServicesByCategory(String category){
    	try {
            if (category == null || category.trim().isEmpty()) {
                throw new IllegalArgumentException("Category must not be null or empty.");
            }
            return serviceRepo.findByCategoryIgnoreCase(category.trim());
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve services by category: " + e.getMessage(), e);
        }
    }

    

    /**
     * Saves the uploaded image files to disk and returns a list of public URLs.
     */
    private List<String> saveImages(List<MultipartFile> images) {
        List<String> imageUrls = new ArrayList<>();
        if (images == null || images.isEmpty()) return imageUrls;

        // Ensure the directory exists
        File dir = new File(servicePath);
        if (!dir.exists()) dir.mkdirs();

        for (MultipartFile file : images) {
            if (file != null && !file.isEmpty()) {
                try {
                    String originalFilename = file.getOriginalFilename();
                    String uniqueFileName = UUID.randomUUID() + "_" + originalFilename;
                    String fullPath = servicePath + uniqueFileName;

                    // Save file to disk
                    file.transferTo(new File(fullPath));

                    // Convert local path to public URL
                    String publicUrl = fullPath
                            .replace(LOCAL_URL_BASE, PUBLIC_URL_BASE)
                            .replace("\\", "/");

                    imageUrls.add(publicUrl);

                } catch (Exception e) {
                    throw new RuntimeException("Error saving image: " + e.getMessage());
                }
            }
        }
        return imageUrls;
    }


	@Override
	public ClavritService getServicesBySlug(String slug) {
		   ClavritService service = serviceRepo.findBySlug(slug)
		            .orElseThrow(() -> new RuntimeException("Service not found with slug: " + slug));
		    return service;
	}
}
