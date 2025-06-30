package com.clavrit.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.clavrit.Dto.JobApplicationRequestDto;
import com.clavrit.Service.JobApplicationService;
import com.clavrit.response.ApiResponse;

@RestController
@RequestMapping("/api/job-application")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    @PostMapping("/apply")
    public ResponseEntity<ApiResponse<Long>> applyToJob(
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String coverLetter,
            @RequestParam(required = false) MultipartFile uploadResume
    ) {
        JobApplicationRequestDto dto = new JobApplicationRequestDto();
        dto.setFullName(fullName);
        dto.setEmail(email);
        dto.setPhone(phone);
        dto.setCoverLetter(coverLetter);
        dto.setUploadResume(uploadResume);

        ApiResponse<Long> response = jobApplicationService.applyToJob(dto);
        return ResponseEntity.ok(response);
    }
}
