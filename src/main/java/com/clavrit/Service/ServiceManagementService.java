package com.clavrit.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.clavrit.Dto.ServiceDto;
import com.clavrit.Entity.ClavritService;

public interface ServiceManagementService {

	
	 ClavritService createService(ServiceDto dto, List<MultipartFile> images) throws IOException;

	    List<ClavritService> getAllServices();

	    Optional<ClavritService> getService(Long id);

	    ClavritService updateService(Long id, ServiceDto dto, List<MultipartFile> images );

	    void deleteService(Long id);
}
