package com.clavrit.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.clavrit.Dto.BusinessStatItemDto;

public interface BusinessStatsService {
	
	 // Create
    BusinessStatItemDto createStat(BusinessStatItemDto dto, MultipartFile multipartFile);

    // Read
    List<BusinessStatItemDto> getAllStats();
    BusinessStatItemDto getStatById(Long id);

    // Update
    BusinessStatItemDto updateStat(BusinessStatItemDto dto, MultipartFile file);

    // Delete
    void deleteStat(Long id);

}
