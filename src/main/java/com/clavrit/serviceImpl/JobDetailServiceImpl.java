package com.clavrit.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	        return jobDetailRepository.saveAll(jobs);
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to save jobs: " + e.getMessage());
	    }
	}
    
}
