package com.clavrit.Service;

import com.clavrit.Dto.JobApplicationRequestDto;
import com.clavrit.response.ApisResponse;

public interface JobApplicationService {
	 ApisResponse applyToJob(JobApplicationRequestDto dto);
	    ApisResponse getJobApplication(Long id);
	    ApisResponse getAllJobApplications();
	    ApisResponse updateJobApplication(Long id, JobApplicationRequestDto dto);
	    ApisResponse deleteJobApplication(Long id);
}

