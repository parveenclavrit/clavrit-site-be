package com.clavrit.Service;

import com.clavrit.Dto.JobDetailDTO;

public interface JobDetailService {
    JobDetailDTO save(JobDetailDTO dto);
    JobDetailDTO getById(Long id);
}

