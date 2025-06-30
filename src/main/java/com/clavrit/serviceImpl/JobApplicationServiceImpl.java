package com.clavrit.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clavrit.Dto.JobApplicationRequestDto;
import com.clavrit.Entity.JobApplication;
import com.clavrit.Repository.JobApplicationRepository;
import com.clavrit.Service.JobApplicationService;
import com.clavrit.mapper.JobApplicationMapper;
import com.clavrit.response.ApiResponse;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    @Autowired
    private JobApplicationMapper mapper;

    @Autowired
    private JobApplicationRepository repository;

    @Override
    public ApiResponse<Long> applyToJob(JobApplicationRequestDto dto) {
        JobApplication entity = mapper.toEntity(dto);
        JobApplication saved = repository.save(entity);

        return new ApiResponse<>(
                true,
                "Application submitted successfully",
                saved.getId()
        );
    }
}
