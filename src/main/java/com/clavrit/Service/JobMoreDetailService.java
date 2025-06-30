package com.clavrit.Service;

import com.clavrit.Dto.JobMoreDetailDTO;

public interface JobMoreDetailService {
    JobMoreDetailDTO save(JobMoreDetailDTO dto);
    JobMoreDetailDTO getById(Long id);
}
