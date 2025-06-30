package com.clavrit.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clavrit.Dto.JobMoreDetailDTO;
import com.clavrit.Entity.JobMoreDetail;
import com.clavrit.Repository.JobMoreDetailRepository;
import com.clavrit.mapper.JobMoreDetailMapper;
import com.clavrit.response.ApiResponse;

@RestController
@RequestMapping("/api/job-more-details")
public class JobMoreDetailController {

    @Autowired
    private JobMoreDetailRepository jobMoreDetailRepository;

    @Autowired
    private JobMoreDetailMapper mapper;

    @PostMapping
    public ResponseEntity<ApiResponse<JobMoreDetailDTO>> createJobMoreDetail(
            @RequestBody JobMoreDetailDTO dto) {

        // Convert DTO → Entity
        JobMoreDetail entity = mapper.toEntity(dto);

        // Save to DB
        JobMoreDetail saved = jobMoreDetailRepository.save(entity);

        // Convert saved Entity → DTO
        JobMoreDetailDTO responseDto = mapper.toDTO(saved);

        ApiResponse<JobMoreDetailDTO> response = new ApiResponse<>(
                true,
                "Job more detail created successfully",
                responseDto
        );

        return ResponseEntity.ok(response);
    }
}
