package com.clavrit.serviceImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.clavrit.Dto.BusinessStatItemDto;
import com.clavrit.Entity.BusinessStatItem;
import com.clavrit.Repository.BusinessStatItemRepository;
import com.clavrit.Service.BusinessStatsService;


@Service
public class BusinessStatsServiceImpl implements BusinessStatsService {

    @Autowired
    private BusinessStatItemRepository repository;
    
    @Value("${file.upload.businessStats}")
    private String basePath;
    
    @Value("${LOCAL_BASE_PATH}")
    private String LOCAL_BASE_PATH;
    
    @Value("${PUBLIC_URL_BASE}")
    private String PUBLIC_URL_BASE;

    private BusinessStatItemDto convertToDto(BusinessStatItem entity) {
        return new BusinessStatItemDto(entity.getId(), entity.getTitle(), entity.getValue(), entity.getIconUrl());
    }

    private BusinessStatItem convertToEntity(BusinessStatItemDto dto) {
        BusinessStatItem item = new BusinessStatItem();
        item.setTitle(dto.getTitle());
        item.setValue(dto.getValue());
        return item;
    }

    @Override
    public BusinessStatItemDto createStat(BusinessStatItemDto dto, MultipartFile multipartFile) {
        BusinessStatItem item = convertToEntity(dto);

        if (multipartFile != null && !multipartFile.isEmpty()) {
            try {
                String imageUrl = saveUploadedFile(multipartFile);
                item.setIconUrl(imageUrl); // assuming your entity field is 'icon' not 'iconUrl'
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Image upload failed", e);
            }
        }
        return convertToDto(repository.save(item));
    }


    @Override
    public List<BusinessStatItemDto> getAllStats() {
        return repository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BusinessStatItemDto getStatById(Long id) {
        BusinessStatItem item = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Stat not found with id: " + id));
        return convertToDto(item);
    }

    @Override
    public BusinessStatItemDto updateStat(BusinessStatItemDto dto, MultipartFile iconFile) {
    	 try {
    	        Optional<BusinessStatItem> optional = repository.findById(dto.getId());
    	        if (!optional.isPresent()) {
    	            throw new RuntimeException("Business stat not found with ID: " + dto.getId());
    	        }

    	        BusinessStatItem existing = optional.get();

    	        // Update fields if provided
    	        if (dto.getTitle() != null) existing.setTitle(dto.getTitle());
    	        if (dto.getValue() != null) existing.setValue(dto.getValue());

    	        // Handle new icon upload
    	        if (iconFile != null && !iconFile.isEmpty()) {
    	            String imageUrl = saveUploadedFile(iconFile);
    	            existing.setIconUrl(imageUrl); // or setIconUrl(imageUrl) if your field is named differently
    	        }

    	        BusinessStatItem updated = repository.save(existing);
    	        return convertToDto(updated);

    	    } catch (Exception e) {
    	        
    	        throw new RuntimeException("Failed to update stat: " + e.getMessage());
    	    }
    	}

    @Override
    public void deleteStat(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Stat not found with id: " + id);
        }
        repository.deleteById(id);
    }
    
    private String saveUploadedFile(MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty or null");
        }

        File dir = new File(basePath);
        if (!dir.exists()) dir.mkdirs();

        String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String fullPath = basePath + uniqueFileName;

        file.transferTo(new File(fullPath));

        return fullPath.replace(LOCAL_BASE_PATH, PUBLIC_URL_BASE).replace("\\", "/");
    }
}
