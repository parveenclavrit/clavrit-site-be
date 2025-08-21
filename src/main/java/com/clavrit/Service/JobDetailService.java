package com.clavrit.Service;

import java.util.List;

import com.clavrit.Dto.BlogDto;
import com.clavrit.Dto.JobDetailDTO;
import com.clavrit.Entity.Blog;
import com.clavrit.Entity.JobDetail;

public interface JobDetailService {
	
    JobDetailDTO save(JobDetailDTO dto);
    
    JobDetailDTO getById(Long id);
    
    List<JobDetailDTO> getAllJobs();
    
    JobDetailDTO update(Long id, JobDetailDTO dto);
    
    String delete(Long id);
    List<JobDetail> saveAllJobs(List<JobDetail> jobs);

	JobDetail getJobBySlug(String slug);
}

