package com.clavrit.Service;

import com.clavrit.Dto.JobApplicationRequestDto;
import com.clavrit.response.ApiResponse;

public interface JobApplicationService {
    ApiResponse<Long> applyToJob(JobApplicationRequestDto dto);
}
