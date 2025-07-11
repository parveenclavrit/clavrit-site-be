package com.clavrit.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.clavrit.Dto.JobApplicationRequestDto;
import com.clavrit.Service.JobApplicationService;
import com.clavrit.response.ApisResponse;

@RestController
@RequestMapping("/api/job-application")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    
    @PostMapping("/apply")
    public ResponseEntity<ApisResponse> applyToJob(
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String jobAppliedFor,
            @RequestParam String qualification,
            @RequestParam String totalYOE,
            @RequestParam String relevantExperience,
            @RequestParam String currentCompany,
            @RequestParam String currentCTC,
            @RequestParam String noticePeriod,
            @RequestParam String coverLetter,
            @RequestParam(required = false) MultipartFile uploadResume
    ) {
        JobApplicationRequestDto dto = new JobApplicationRequestDto();
        dto.setFullName(fullName);
        dto.setEmail(email);
        dto.setPhone(phone);
        dto.setJobAppliedFor(jobAppliedFor);
        dto.setQualification(qualification);
        dto.setTotalYOE(totalYOE);
        dto.setRelevantExperience(relevantExperience);
        dto.setCurrentCompany(currentCompany);
        dto.setCurrentCTC(currentCTC);
        dto.setNoticePeriod(noticePeriod);
        dto.setCoverLetter(coverLetter);
        dto.setUploadResume(uploadResume);

        return ResponseEntity.ok(jobApplicationService.applyToJob(dto));
    }

    
    @GetMapping("/all")
    public ResponseEntity<ApisResponse> getAllApplications() {
        return ResponseEntity.ok(jobApplicationService.getAllJobApplications());
    }

  
    @GetMapping("/{id}")
    public ResponseEntity<ApisResponse> getApplicationById(@PathVariable Long id) {
        return ResponseEntity.ok(jobApplicationService.getJobApplication(id));
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<ApisResponse> updateApplication(
            @PathVariable Long id,
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String jobAppliedFor,
            @RequestParam String qualification,
            @RequestParam String totalYOE,
            @RequestParam String relevantExperience,
            @RequestParam String currentCompany,
            @RequestParam String currentCTC,
            @RequestParam String noticePeriod,
            @RequestParam String coverLetter,
            @RequestParam(required = false) MultipartFile uploadResume
    ) {
        JobApplicationRequestDto dto = new JobApplicationRequestDto();
        dto.setFullName(fullName);
        dto.setEmail(email);
        dto.setPhone(phone);
        dto.setJobAppliedFor(jobAppliedFor);
        dto.setQualification(qualification);
        dto.setTotalYOE(totalYOE);
        dto.setRelevantExperience(relevantExperience);
        dto.setCurrentCompany(currentCompany);
        dto.setCurrentCTC(currentCTC);
        dto.setNoticePeriod(noticePeriod);
        dto.setCoverLetter(coverLetter);
        dto.setUploadResume(uploadResume);

        return ResponseEntity.ok(jobApplicationService.updateJobApplication(id, dto));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApisResponse> deleteApplication(@PathVariable Long id) {
        return ResponseEntity.ok(jobApplicationService.deleteJobApplication(id));
    }
}
