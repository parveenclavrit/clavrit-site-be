package com.clavrit.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.clavrit.Dto.ServiceDto;
import com.clavrit.Entity.ClavritService;
import com.clavrit.Enums.ApiStatus;
import com.clavrit.Service.ServiceManagementService;
import com.clavrit.response.ApisResponse;

@RestController
@RequestMapping("/api/services")
public class ClavritServiceController {

	@Autowired
	private ServiceManagementService serviceService;

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ApisResponse createService(
	        @RequestParam("title") String title,
	        @RequestParam("subheading") String subheading,
	        @RequestParam("description") String description,
	        @RequestParam("content") String content,
	        @RequestPart("images") List<MultipartFile> files) {
	    try {
	        // Build DTO manually
	        ServiceDto dto = new ServiceDto();
	        dto.setTitle(title);
	        dto.setSubheading(subheading);
	        dto.setDescription(description);
	        dto.setContent(content);

	        ClavritService createdService = serviceService.createService(dto, files);
	        return new ApisResponse(ApiStatus.CREATED, "Service created successfully", createdService);
	    } catch (Exception e) {
	        return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Error creating service", e.getMessage());
	    }
	}



	@GetMapping
	public ApisResponse getAllServices() {
	    try {
	        List<ClavritService> services = serviceService.getAllServices();
	        return new ApisResponse(ApiStatus.OK, "Fetched all services", services);
	    } catch (Exception e) {
	        return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Error fetching services", e.getMessage());
	    }
	}


	@GetMapping("/{id}")
	public ApisResponse getServiceById(@PathVariable Long id) {
	    try {
	        return serviceService.getService(id)
	            .map(service -> new ApisResponse(ApiStatus.OK, "Service found", service))
	            .orElse(new ApisResponse(ApiStatus.NOT_FOUND, "Service not found", null));
	    } catch (Exception e) {
	        return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Error fetching service", e.getMessage());
	    }
	}


	@PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ApisResponse updateService(
	        @PathVariable Long id,
	        @RequestParam("title") String title,
	        @RequestParam("subheading") String subheading,
	        @RequestParam("description") String description,
	        @RequestParam("content") String content,
	        @RequestPart(value = "images", required = false) List<MultipartFile> images) {
	    try {
	        ServiceDto dto = new ServiceDto();
	        dto.setTitle(title);
	        dto.setSubheading(subheading);
	        dto.setDescription(description);
	        dto.setContent(content);

	        ClavritService updated = serviceService.updateService(id, dto, images);
	        return new ApisResponse(ApiStatus.OK, "Service updated successfully", updated);
	    } catch (Exception e) {
	        return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Error updating service", e.getMessage());
	    }
	}





	@DeleteMapping("/{id}")
	public ApisResponse deleteService(@PathVariable Long id) {
	    try {
	        serviceService.deleteService(id);
	        return new ApisResponse(ApiStatus.OK, "Service deleted successfully", null);
	    } catch (Exception e) {
	        return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Error deleting service", e.getMessage());
	    }
	}

}
