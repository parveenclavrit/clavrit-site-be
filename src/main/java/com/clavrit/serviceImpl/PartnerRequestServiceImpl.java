package com.clavrit.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.clavrit.Dto.PartnerRequestDto;
import com.clavrit.Entity.PartnerRequest;
import com.clavrit.Repository.PartnerRequestRepository;
import com.clavrit.Service.PartnerRequestService;
import com.clavrit.mapper.PartnerRequestMapper;

@Service
public class PartnerRequestServiceImpl implements PartnerRequestService{
	
	Logger log = LoggerFactory.getLogger(PartnerRequestServiceImpl.class);
	
	@Value("${contact.receiver.email}")
    private String toEmail;
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	private PartnerRequestRepository repository;
	
	@Autowired
	private PartnerRequestMapper mapper;

	@Override
	public PartnerRequestDto createRequest(PartnerRequestDto dto) {
		try {
			if (repository.existsByCompanyNameIgnoreCaseAndEmailIgnoreCase(dto.getCompanyName(), dto.getEmail())) {
				
	            throw new RuntimeException("Duplicate partner request already exists for this company and email.");
	        }
			PartnerRequest entity = mapper.toEntity(dto);
			PartnerRequest saved = repository.save(entity);
			log.info("Partner request created by {}", saved.getEmail());
			
			sendNotificationEmail(dto);
			
            return mapper.toDto(saved);
            
		} catch (Exception e) {
			log.error("Failed to create partner request: {}", e.getMessage());
			throw new RuntimeException("Error creating partner request");
        }
	}

	@Override
	public PartnerRequestDto getRequestById(Long id) {
		try {
            PartnerRequest entity = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Partner request not found with ID: " + id));
            return mapper.toDto(entity);
        } catch (Exception e) {
            log.error("Failed to fetch partner request with ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error fetching partner request");
        }
	}

	@Override
	public List<PartnerRequestDto> getAllRequests() {
		try {
	        List<PartnerRequest> entities = repository.findAll();
	        List<PartnerRequestDto> dtos = new ArrayList<>();

	        for (PartnerRequest entity : entities) {
	            PartnerRequestDto dto = mapper.toDto(entity);
	            dtos.add(dto);
	        }

	        log.info("Fetched {} partner requests", dtos.size());
	        return dtos;

	    } catch (Exception e) {
	        log.error("Failed to fetch all partner requests: {}", e.getMessage());
	        throw new RuntimeException("Error fetching partner requests");
	    }
	}

	@Override
	public PartnerRequestDto updateRequest(Long id, PartnerRequestDto dto) {
		try {
	        Optional<PartnerRequest> optional = repository.findById(id);
	        if (!optional.isPresent()) {
	            throw new RuntimeException("Partner request not found with ID: " + id);
	        }

	        PartnerRequest existing = optional.get();

	        PartnerRequest updatedEntity = mapper.toEntity(dto);
	        updatedEntity.setId(existing.getId()); // Preserve ID
	        updatedEntity.setSubmittedAt(existing.getSubmittedAt()); // Preserve submission date

	        PartnerRequest updated = repository.save(updatedEntity);
	        log.info("Partner request updated with ID: {}", id);
	        return mapper.toDto(updated);

	    } catch (Exception e) {
	        log.error("Failed to update partner request ID {}: {}", id, e.getMessage());
	        throw new RuntimeException("Error updating partner request");
	    }
	}

	@Override
	public String deleteRequest(Long id) {
		try {
	        Optional<PartnerRequest> optional = repository.findById(id);
	        if (!optional.isPresent()) {
	            throw new RuntimeException("Partner request not found with ID: " + id);
	        }

	        PartnerRequest entity = optional.get();
	        repository.delete(entity);

	        log.info("Partner request deleted with ID: {}", id);
	        return "Partner request deleted successfully with ID: " + id;

	    } catch (Exception e) {
	        log.error("Failed to delete partner request ID {}: {}", id, e.getMessage());
	        throw new RuntimeException("Error deleting partner request");
	    }
	}
	
	private void sendNotificationEmail(PartnerRequestDto dto) {
        try {
        	SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("New Partner Request from " + dto.getFullName());

            message.setText(
                "You have received a new partner request!\n\n" +
                "Full Name   : " + dto.getFullName() + "\n" +
                "Company     : " + dto.getCompanyName() + "\n" +
                "Email       : " + dto.getEmail() + "\n" +
                "Phone       : " + dto.getPhone() + "\n\n" +
                "Message:\n" +
                "--------------------------------------------------\n" +
                dto.getMessage() + "\n" +
                "--------------------------------------------------\n"
            );

            mailSender.send(message);
            log.info("Partner request notification sent to: {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", toEmail, e.getMessage());
        }
    }
	@Override
	public List<PartnerRequest> saveAllPartnerRequests(List<PartnerRequest> requests) {
	    try {
	    	if (requests == null || requests.isEmpty()) return new ArrayList<>();

	        List<PartnerRequest> existingRequests = repository.findAll();
	        
	        Map<String, PartnerRequest> existingMap = new HashMap<>();
	        for (PartnerRequest existing : existingRequests) {
	            String key = (existing.getCompanyName() + "|" + existing.getEmail()).toLowerCase().trim();
	            existingMap.put(key, existing);
	        }

	        Set<String> seenKeys = new HashSet<>();
	        List<PartnerRequest> toSave = new ArrayList<>();

	        for (PartnerRequest incoming : requests) {
	            String key = (incoming.getCompanyName() + "|" + incoming.getEmail()).toLowerCase().trim();

	            if (seenKeys.contains(key)) {
	                log.warn("Duplicate in incoming list skipped for: {}", key);
	                continue;
	            }
	            seenKeys.add(key);

	            if (existingMap.containsKey(key)) {
	                PartnerRequest existing = existingMap.get(key);

	                existing.setFullName(incoming.getFullName());
	                existing.setPhone(incoming.getPhone());
	                existing.setMessage(incoming.getMessage());
	                existing.setSubmittedAt(incoming.getSubmittedAt() != null ? incoming.getSubmittedAt() : existing.getSubmittedAt());

	                toSave.add(existing);
	                log.info("Updated partner request for: {}", key);
	            } else {
	                toSave.add(incoming);
	                log.info("New partner request added for: {}", key);
	            }
	        }

	        List<PartnerRequest> saved = repository.saveAll(toSave);
	        log.info("Successfully saved {} partner requests (new + updated)", saved.size());
	        return saved;
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to save partner requests: " + e.getMessage());
	    }
	}

}
