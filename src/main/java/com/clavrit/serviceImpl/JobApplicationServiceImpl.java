package com.clavrit.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.clavrit.Dto.JobApplicationRequestDto;
import com.clavrit.Entity.JobApplication;
import com.clavrit.Repository.JobApplicationRepository;
import com.clavrit.Service.JobApplicationService;
import com.clavrit.mapper.JobApplicationMapper;
import com.clavrit.response.ApiResponse;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

	
	 @Value("${file.upload.resume}")
		private String resumeLocalPath;
	    
	    private static final String PUBLIC_URL_BASE = "http://157.20.190.17";
	    private static final String LOCAL_URL_BASE = "/home/ubuntu/clavrit-website";
    @Autowired
    private JobApplicationMapper mapper;

    @Autowired
    private JobApplicationRepository repository;

    @Override
    public ApiResponse<Long> applyToJob(JobApplicationRequestDto dto) {
    	
        JobApplication entity = mapper.toEntity(dto);
        // Save resume if uploaded
        if (dto.getUploadResume() != null && !dto.getUploadResume().isEmpty()) {
            try {
				saveResumeToDisk(dto.getUploadResume(), entity);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        JobApplication saved = repository.save(entity);

        return new ApiResponse<>(
                true,
                "Application submitted successfully",
                saved.getId()
        );
    }
    private void saveResumeToDisk(MultipartFile resumeFile, JobApplication entity) throws IOException {
       
        File dir = new File(resumeLocalPath);
        if (!dir.exists()) dir.mkdirs();

        String uniqueFileName = UUID.randomUUID() + "_" + resumeFile.getOriginalFilename();
        String fullPath = resumeLocalPath + uniqueFileName;

        resumeFile.transferTo(new File(fullPath));
        String publicUrl = fullPath
                .replace(LOCAL_URL_BASE, PUBLIC_URL_BASE)
                .replace("\\", "/");
        // Save only path, name, and type
        entity.setResumeFilePath(publicUrl);
        entity.setResumeFileName(resumeFile.getOriginalFilename());
        entity.setResumeFileType(resumeFile.getContentType());
    }

}
