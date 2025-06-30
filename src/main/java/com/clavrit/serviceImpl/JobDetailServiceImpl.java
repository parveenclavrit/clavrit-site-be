package com.clavrit.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clavrit.Dto.JobDetailDTO;
import com.clavrit.Entity.JobDetail;
import com.clavrit.Entity.JobMoreDetail;
import com.clavrit.Repository.JobDetailRepository;
import com.clavrit.Repository.JobMoreDetailRepository;
import com.clavrit.Service.JobDetailService;
import com.clavrit.mapper.JobDetailMapper;

@Service
public class JobDetailServiceImpl implements JobDetailService {

    @Autowired
    private JobDetailRepository jobDetailRepository;

    @Autowired
    private JobMoreDetailRepository jobMoreDetailRepository;

    @Override
    public JobDetailDTO save(JobDetailDTO dto) {
        JobDetail entity = new JobDetail();
        entity.setJobCategory(dto.getJobCategory());
        entity.setJobDesignation(dto.getJobDesignation());
        entity.setJobType(dto.getJobType());

        if (dto.getMoreDetails() != null && dto.getMoreDetails().getId() != null) {
            JobMoreDetail moreDetails = jobMoreDetailRepository
                    .findById(dto.getMoreDetails().getId())
                    .orElse(null);
            entity.setMoreDetails(moreDetails);
        }

        JobDetail saved = jobDetailRepository.save(entity);
        return JobDetailMapper.toDTO(saved);
    }

    @Override
    public JobDetailDTO getById(Long id) {
        return jobDetailRepository.findById(id)
                .map(JobDetailMapper::toDTO)
                .orElse(null);
    }
}
