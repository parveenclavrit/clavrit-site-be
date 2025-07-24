package com.clavrit.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clavrit.Dto.JobDetailDTO;
import com.clavrit.Entity.JobDetail;
import com.clavrit.Repository.JobDetailRepository;
import com.clavrit.Service.JobDetailService;
import com.clavrit.mapper.JobDetailMapper;

@Service
public class JobDetailServiceImpl implements JobDetailService {

    @Autowired
    private JobDetailRepository jobDetailRepository;
    
    Logger log = LoggerFactory.getLogger(JobDetailServiceImpl.class);
    
    @Autowired
    private JobDetailMapper jobDetailMapper;

	@Override
	public JobDetailDTO save(JobDetailDTO dto) {
		try {
			if (jobDetailRepository.existsByJobDesignationIgnoreCase(dto.getJobDesignation())) {
	            log.warn("Duplicate job designation found: {}", dto.getJobDesignation());
	            throw new RuntimeException("Job with the same designation already exists");
	        }
            JobDetail entity = jobDetailMapper.toEntity(dto);
            entity.setCreateAt(LocalDateTime.now());
            entity.setUpdatedAt(LocalDateTime.now());

            JobDetail saved = jobDetailRepository.save(entity);
            log.info("Job saved successfully with ID: {}", saved.getId());
            return jobDetailMapper.toDTO(saved);
        } catch (Exception e) {
            log.error("Error while saving job: {}", e.getMessage());
            throw new RuntimeException("Failed to save job detail");
        }
	}

	@Override
	public JobDetailDTO getById(Long id) {
		try {
            Optional<JobDetail> optional = jobDetailRepository.findById(id);
            if (!optional.isPresent()) {
                throw new RuntimeException("Job not found with ID: " + id);
            }
            log.info("Job fetched with ID: {}", id);
            return jobDetailMapper.toDTO(optional.get());
        } catch (Exception e) {
            log.error("Error fetching job by ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to fetch job detail");
        }
	}

	@Override
	public List<JobDetailDTO> getAllJobs() {
		try {
            List<JobDetail> jobs = jobDetailRepository.findAll();
            List<JobDetailDTO> dtos = new ArrayList<>();
            for (JobDetail job : jobs) {
                dtos.add(jobDetailMapper.toDTO(job));
            }
            log.info("Total {} jobs fetched", dtos.size());
            return dtos;
        } catch (Exception e) {
            log.error("Error fetching all jobs: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch job details");
        }
	}

	@Override
	public JobDetailDTO update(Long id, JobDetailDTO dto) {
		try {
            Optional<JobDetail> optional = jobDetailRepository.findById(id);
            if (!optional.isPresent()) {
                throw new RuntimeException("Job not found with ID: " + id);
            }

            JobDetail existing = optional.get();

            if (dto.getJobDesignation() != null) existing.setJobDesignation(dto.getJobDesignation());
            if (dto.getJobCategory() != null) existing.setJobCategory(dto.getJobCategory());
            if (dto.getJobLocation() != null) existing.setJobLocation(dto.getJobLocation());
            if (dto.getIndustry() != null) existing.setIndustry(dto.getIndustry());
            if (dto.getJobResponsibility() != null) existing.setJobResponsibility(dto.getJobResponsibility());
            if (dto.getJobQualification() != null) existing.setJobQualification(dto.getJobQualification());
            if (dto.getCompetencies() != null) existing.setCompetencies(dto.getCompetencies());
            if (dto.getJobType() != null) existing.setJobType(dto.getJobType());

            existing.setUpdatedAt(LocalDateTime.now());

            JobDetail updated = jobDetailRepository.save(existing);
            log.info("Job updated successfully with ID: {}", id);
            return jobDetailMapper.toDTO(updated);

        } catch (Exception e) {
            log.error("Error updating job with ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to update job detail");
        }
	}

	@Override
	public String delete(Long id) {
		try {
            Optional<JobDetail> optional = jobDetailRepository.findById(id);
            if (!optional.isPresent()) {
                throw new RuntimeException("Job not found with ID: " + id);
            }

            jobDetailRepository.delete(optional.get());
            log.info("Job deleted with ID: {}", id);
            return "Job deleted successfully with ID: " + id;

        } catch (Exception e) {
            log.error("Error deleting job with ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to delete job detail");
        }
	}
	@Override
	public List<JobDetail> saveAllJobs(List<JobDetail> jobs) {
	    try {
	    	if (jobs == null || jobs.isEmpty()) return Collections.emptyList();

	        List<JobDetail> existingJobs = jobDetailRepository.findAll();

	        Map<String, JobDetail> existingMap = new HashMap<>();
	        for (JobDetail job : existingJobs) {
	            existingMap.put(job.getJobDesignation().trim().toLowerCase(), job);
	        }

	        Set<String> processedDesignations = new HashSet<>();
	        List<JobDetail> toSave = new ArrayList<>();

	        for (JobDetail incoming : jobs) {
	            String designationKey = incoming.getJobDesignation().trim().toLowerCase();

	            if (processedDesignations.contains(designationKey)) {
	                log.warn("Duplicate job in input skipped: {}", incoming.getJobDesignation());
	                continue;
	            }
	            processedDesignations.add(designationKey);

	            if (existingMap.containsKey(designationKey)) {
	                
	                JobDetail existing = existingMap.get(designationKey);

	                if (incoming.getJobCategory() != null) existing.setJobCategory(incoming.getJobCategory());
	                if (incoming.getJobLocation() != null) existing.setJobLocation(incoming.getJobLocation());
	                if (incoming.getIndustry() != null) existing.setIndustry(incoming.getIndustry());
	                
	                existing.setJobResponsibility(incoming.getJobResponsibility());
	                existing.setJobQualification(incoming.getJobQualification());
	                existing.setCompetencies(incoming.getCompetencies());

	                if (incoming.getJobType() != null) existing.setJobType(incoming.getJobType());

	                existing.setUpdatedAt(LocalDateTime.now());
	                toSave.add(existing);

	                log.info("Job updated for designation: {}", existing.getJobDesignation());
	            } else {
	            	
	                incoming.setCreateAt(LocalDateTime.now());
	                incoming.setUpdatedAt(LocalDateTime.now());
	                toSave.add(incoming);
	            }
	        }
	        log.info("Job saved+ updated");
	        return jobDetailRepository.saveAll(toSave);
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to save jobs: " + e.getMessage());
	    }
	}
    
}
