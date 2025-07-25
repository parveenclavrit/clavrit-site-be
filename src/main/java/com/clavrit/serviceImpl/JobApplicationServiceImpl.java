package com.clavrit.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.clavrit.Dto.JobApplicationRequestDto;
import com.clavrit.Entity.JobApplication;
import com.clavrit.Enums.ApiStatus;
import com.clavrit.Repository.JobApplicationRepository;
import com.clavrit.Service.JobApplicationService;
import com.clavrit.mapper.JobApplicationMapper;
import com.clavrit.response.ApiResponse;
import com.clavrit.response.ApisResponse;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

	
	 @Value("${file.upload.resume}")
	 private String resumeLocalPath;
	 
	 @Value("${LOCAL_BASE_PATH}")
	 private String LOCAL_URL_BASE;
	    
	 @Value("${PUBLIC_URL_BASE}")
	 private String PUBLIC_URL_BASE;
	 
    @Autowired
    private JobApplicationMapper mapper;

    @Autowired
    private JobApplicationRepository repository;

    @Override
    public ApisResponse applyToJob(JobApplicationRequestDto dto) {
        try {
        	boolean exists = repository.existsByEmailIgnoreCaseAndPhoneAndJobAppliedForIgnoreCase(
                    dto.getEmail(), dto.getPhone(), dto.getJobAppliedFor());

            if (exists) {
                return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Application already submitted with the same details", null);
            }
            JobApplication entity = mapper.toEntity(dto);

            if (dto.getUploadResume() != null && !dto.getUploadResume().isEmpty()) {
                saveResumeToDisk(dto.getUploadResume(), entity);
            }

            JobApplication saved = repository.save(entity);

            return new ApisResponse(ApiStatus.CREATED, "Application submitted successfully", saved.getId());
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Failed to apply", e.getMessage());
        }
    }
    @Override
    public List<JobApplication> saveAllJobApplications(List<JobApplication> applications) {
        try {
        	if (applications == null || applications.isEmpty()) return Collections.emptyList();

            // Fetch all existing applications
            List<JobApplication> existingApplications = repository.findAll();

            Map<String, JobApplication> existingMap = new HashMap<>();
            for (JobApplication existing : existingApplications) {
                String key = (existing.getEmail().toLowerCase() + "|" +
                              existing.getPhone().trim() + "|" +
                              existing.getJobAppliedFor().toLowerCase()).trim();
                existingMap.put(key, existing);
            }

            Set<String> processedKeys = new HashSet<>();
            List<JobApplication> toSave = new ArrayList<>();

            for (JobApplication incoming : applications) {
                String key = (incoming.getEmail().toLowerCase() + "|" +
                              incoming.getPhone().trim() + "|" +
                              incoming.getJobAppliedFor().toLowerCase()).trim();

                if (processedKeys.contains(key)) {
                    continue; 
                }

                processedKeys.add(key);

                if (existingMap.containsKey(key)) {
                    JobApplication existing = existingMap.get(key);
                    
                    existing.setFullName(incoming.getFullName());
                    existing.setQualification(incoming.getQualification());
                    existing.setTotalYOE(incoming.getTotalYOE());
                    existing.setRelevantExperience(incoming.getRelevantExperience());
                    existing.setCurrentCompany(incoming.getCurrentCompany());
                    existing.setCurrentCTC(incoming.getCurrentCTC());
                    existing.setNoticePeriod(incoming.getNoticePeriod());
                    existing.setCoverLetter(incoming.getCoverLetter());
                    
                    toSave.add(existing);
                } else {
                    
                    toSave.add(incoming);
                }
            }

            return repository.saveAll(toSave);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save job applications: " + e.getMessage());
        }
    }
    @Override
    public ApisResponse getJobApplication(Long id) {
        try {
            JobApplication job = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Application not found with ID: " + id));
            return new ApisResponse(ApiStatus.OK, "Fetched successfully", job);
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Failed to fetch", e.getMessage());
        }
    }

    @Override
    public ApisResponse getAllJobApplications() {
        try {
            return new ApisResponse(ApiStatus.OK, "All applications fetched", repository.findAll());
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Failed to fetch all", e.getMessage());
        }
    }

    @Override
    public ApisResponse updateJobApplication(Long id, JobApplicationRequestDto dto) {
        try {
            JobApplication existing = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Application not found"));

            existing.setFullName(dto.getFullName());
            existing.setEmail(dto.getEmail());
            existing.setPhone(dto.getPhone());
            existing.setJobAppliedFor(dto.getJobAppliedFor());
            existing.setQualification(dto.getQualification());
            existing.setTotalYOE(dto.getTotalYOE());
            existing.setRelevantExperience(dto.getRelevantExperience());
            existing.setCurrentCompany(dto.getCurrentCompany());
            existing.setCurrentCTC(dto.getCurrentCTC());
            existing.setNoticePeriod(dto.getNoticePeriod());
            existing.setCoverLetter(dto.getCoverLetter());

            if (dto.getUploadResume() != null && !dto.getUploadResume().isEmpty()) {
                saveResumeToDisk(dto.getUploadResume(), existing);
            }

            JobApplication updated = repository.save(existing);
            return new ApisResponse(ApiStatus.OK, "Application updated successfully", updated);

        } catch (Exception e) {
            return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Failed to update", e.getMessage());
        }
    }

    @Override
    public ApisResponse deleteJobApplication(Long id) {
        try {
            if (!repository.existsById(id)) {
                return new ApisResponse(ApiStatus.NOT_FOUND, "Application not found", null);
            }

            repository.deleteById(id);
            return new ApisResponse(ApiStatus.OK, "Application deleted successfully", null);
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Failed to delete", e.getMessage());
        }
    }
    private void saveResumeToDisk(MultipartFile resumeFile, JobApplication entity) throws IOException {
        
    	String oldResumeUrl = entity.getResumeFilePath();
        if (oldResumeUrl != null && !oldResumeUrl.isEmpty()) {
            String oldLocalPath = oldResumeUrl.replace(PUBLIC_URL_BASE, LOCAL_URL_BASE);
            File oldFile = new File(oldLocalPath);
            if (oldFile.exists()) {
                boolean deleted = oldFile.delete();
                if (deleted) {
                    System.out.println("Deleted old resume: " + oldLocalPath);
                } else {
                    System.err.println("Failed to delete old resume: " + oldLocalPath);
                }
            }
        }

        
        File dir = new File(resumeLocalPath);
        if (!dir.exists()) dir.mkdirs();

       
        String uniqueFileName = UUID.randomUUID() + "_" + resumeFile.getOriginalFilename();
        String fullPath = resumeLocalPath + uniqueFileName;

        resumeFile.transferTo(new File(fullPath));

        
        String publicUrl = fullPath
                .replace(LOCAL_URL_BASE, PUBLIC_URL_BASE)
                .replace("\\", "/");

        
        entity.setResumeFilePath(publicUrl);
        entity.setResumeFileName(resumeFile.getOriginalFilename());
        entity.setResumeFileType(resumeFile.getContentType());
    }

}
