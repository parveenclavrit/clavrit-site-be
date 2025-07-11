package com.clavrit.Service;

import java.util.List;

import com.clavrit.Dto.JobDetailDTO;

public interface JobDetailService {
	
    JobDetailDTO save(JobDetailDTO dto);
    
    JobDetailDTO getById(Long id);
    
    List<JobDetailDTO> getAllJobs();
    
    JobDetailDTO update(Long id, JobDetailDTO dto);
    
    String delete(Long id);
}

