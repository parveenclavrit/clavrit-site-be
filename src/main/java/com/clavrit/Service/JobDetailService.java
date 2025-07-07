package com.clavrit.Service;

import java.util.List;

import com.clavrit.Dto.JobDetailDTO;

public interface JobDetailService {
    JobDetailDTO save(JobDetailDTO dto);
    JobDetailDTO getById(Long id);
    List<JobDetailDTO> GetAllJob();
}

