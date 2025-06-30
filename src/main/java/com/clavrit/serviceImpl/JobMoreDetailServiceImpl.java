package com.clavrit.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clavrit.Dto.JobMoreDetailDTO;
import com.clavrit.Entity.JobMoreDetail;
import com.clavrit.Repository.JobMoreDetailRepository;
import com.clavrit.Service.JobMoreDetailService;
import com.clavrit.mapper.JobMoreDetailMapper;

@Service
public class JobMoreDetailServiceImpl implements JobMoreDetailService {

    @Autowired
    private JobMoreDetailRepository repository;

    @Override
    public JobMoreDetailDTO save(JobMoreDetailDTO dto) {
        JobMoreDetail entity = JobMoreDetailMapper.toEntity(dto);
        entity = repository.save(entity);
        return JobMoreDetailMapper.toDTO(entity);
    }

    @Override
    public JobMoreDetailDTO getById(Long id) {
        return repository.findById(id)
                .map(JobMoreDetailMapper::toDTO)
                .orElse(null);
    }
}
