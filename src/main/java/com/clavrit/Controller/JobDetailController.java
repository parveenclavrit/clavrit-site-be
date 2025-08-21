package com.clavrit.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clavrit.Dto.BlogDto;
import com.clavrit.Dto.JobDetailDTO;
import com.clavrit.Entity.Blog;
import com.clavrit.Entity.JobDetail;
import com.clavrit.Enums.ApiStatus;
import com.clavrit.Service.JobDetailService;
import com.clavrit.response.ApisResponse;

@RestController
@RequestMapping("/clavrit/job-details")
public class JobDetailController {

    @Autowired
    private JobDetailService jobDetailService;
    
    @PostMapping
    public ApisResponse createJob(@RequestBody JobDetailDTO dto) {
        try {
            JobDetailDTO saved = jobDetailService.save(dto);
            return new ApisResponse(ApiStatus.CREATED, "Job created successfully.", saved);
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Error creating job.", e.getMessage());
        }
    }
    
    @GetMapping("/slug")
    public ApisResponse getJobBySlug(@RequestParam("slug") String slug) {
        try {
            JobDetail job = jobDetailService.getJobBySlug(slug);
            return new ApisResponse(ApiStatus.OK, "Job fetched successfully", job);
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.NOT_FOUND, "Error fetching blog", e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ApisResponse getJobById(@PathVariable Long id) {
        try {
            JobDetailDTO job = jobDetailService.getById(id);
            return new ApisResponse(ApiStatus.OK, "Job retrieved successfully.", job);
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.NOT_FOUND, "Job not found.", e.getMessage());
        }
    }

    @GetMapping
    public ApisResponse getAllJobs() {
        try {
            List<JobDetailDTO> jobs = jobDetailService.getAllJobs();
            return new ApisResponse(ApiStatus.OK, "Jobs retrieved successfully.", jobs);
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Error retrieving jobs.", e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ApisResponse updateJob(@PathVariable Long id, @RequestBody JobDetailDTO dto) {
        try {
            JobDetailDTO updated = jobDetailService.update(id, dto);
            return new ApisResponse(ApiStatus.OK, "Job updated successfully.", updated);
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Error updating job.", e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ApisResponse deleteJob(@PathVariable Long id) {
        try {
            String message = jobDetailService.delete(id);
            return new ApisResponse(ApiStatus.OK, message, null);
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.NOT_FOUND, "Error deleting job.", e.getMessage());
        }
    }
   
}
