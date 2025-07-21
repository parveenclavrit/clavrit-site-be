package com.clavrit.serviceImpl;

import com.clavrit.Dto.ServiceDto;
import com.clavrit.Entity.ClavritService;
import com.clavrit.Repository.ServiceRepository;
import com.clavrit.Service.ServiceManagementService;
import com.clavrit.mapper.ServiceMapper;
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
public class ServiceManagementServiceImpl implements ServiceManagementService {

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
            List<String> imageUrls = saveImages(files);
            entity.setImageUrl(imageUrls);
            return serviceRepo.save(entity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create service: " + e.getMessage());
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

            // Update only non-null fields (partial update)
            service.setName(dto.getName() != null ? dto.getName() : service.getName());
            service.setType(dto.getType() != null ? dto.getType() : service.getType());
            service.setDescription(dto.getDescription() != null ? dto.getDescription() : service.getDescription());

            // Handle image update
            if (images!= null && !images.isEmpty()) {
                List<String> existingImages = service.getImageUrl();
                if (existingImages != null && !existingImages.isEmpty()) {
                    for (String url : existingImages) {
                        try {
                            // Convert public URL to local file path
                            String localPath = url.replace(PUBLIC_URL_BASE, LOCAL_URL_BASE);

                            File file = new File(localPath);
                            if (file.exists()) {
                                file.delete();
//                                logger.info("Deleted old image: {}", localPath);
                            } else {
//                                logger.warn("File not found for deletion: {}", localPath);
                            }
                        } catch (Exception e) {
//                            logger.warn("Failed to delete image: {}", url, e);
                        }
                    }
                }

                // Save new images
                List<String> imageUrls = saveImages(images);
                service.setImageUrl(imageUrls);
            }

            

            ClavritService updated = serviceRepo.save(service);
//            logger.info("Service with ID {} updated successfully.", id);

            return updated;

        } catch (Exception e) {
//            logger.error("Error updating service with ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to update service: " + e.getMessage());
        }
    }


    @Override
    public void deleteService(Long id) {
        serviceRepo.deleteById(id);
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
}
