package com.clavrit.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clavrit.Dto.JobDetailDTO;
import com.clavrit.Service.JobDetailService;
import com.clavrit.response.ApiResponse;

@RestController
@RequestMapping("/api/job-details")
public class JobDetailController {

    @Autowired
    private JobDetailService jobDetailService;

    @PostMapping
    public ApiResponse<JobDetailDTO> createJobDetail(@RequestBody JobDetailDTO dto) {
        JobDetailDTO saved = jobDetailService.save(dto);
        return new ApiResponse<>(true, "Job detail saved", saved);
    }

    @GetMapping("/{id}")
    public ApiResponse<JobDetailDTO> getJobDetail(@PathVariable Long id) {
        JobDetailDTO dto = jobDetailService.getById(id);
        return new ApiResponse<>(true, "Job detail found", dto);
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<JobDetailDTO>>> getAllJobs() {
        List<JobDetailDTO> jobs = jobDetailService.GetAllJob();

        ApiResponse<List<JobDetailDTO>> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage("All jobs fetched successfully");
        response.setData(jobs);

        return ResponseEntity.ok(response);
    }
}
