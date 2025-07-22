package com.clavrit.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.clavrit.Dto.TechnologiesResponse;
import com.clavrit.Enums.ApiStatus;
import com.clavrit.Service.TechnologiesService;
import com.clavrit.response.ApisResponse;

@RestController
@RequestMapping("/clavrit/technologies")
public class TechnologiesController {

    @Autowired
    private TechnologiesService technologiesService;

    @PostMapping
    public ApisResponse createTechnology(@RequestPart("image") MultipartFile file) {
        try {
            TechnologiesResponse response = technologiesService.save(file);
            return new ApisResponse(ApiStatus.CREATED, "Technology created successfully", response);
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Error creating technology", e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ApisResponse updateTechnology(@PathVariable Long id, @RequestPart("image") MultipartFile file) {
        try {
            TechnologiesResponse response = technologiesService.update(id, file);
            return new ApisResponse(ApiStatus.OK, "Technology updated successfully", response);
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Error updating technology", e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ApisResponse getTechnologyById(@PathVariable Long id) {
        try {
            TechnologiesResponse response = technologiesService.getById(id);
            return new ApisResponse(ApiStatus.OK, "Technology fetched successfully", response);
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.NOT_FOUND, "Error fetching technology", e.getMessage());
        }
    }

    @GetMapping
    public ApisResponse getAllTechnologies() {
        try {
            List<TechnologiesResponse> response = technologiesService.getAll();
            return new ApisResponse(ApiStatus.OK, "All technologies fetched successfully", response);
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Error fetching technologies", e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ApisResponse deleteTechnology(@PathVariable Long id) {
        try {
            String result = technologiesService.delete(id);
            return new ApisResponse(ApiStatus.OK, result, null);
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Error deleting technology", e.getMessage());
        }
    }
}
