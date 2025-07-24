package com.clavrit.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clavrit.Dto.MailRequestDto;
import com.clavrit.Entity.MailRecord;
import com.clavrit.Repository.MailRecordRepository;
import com.clavrit.Service.ContactService;
import com.clavrit.mapper.MailRecordMapper;

import javax.persistence.EntityNotFoundException;

@Service
public class ContactServiceImpl implements ContactService {
	
	@Autowired
	private MailRecordRepository repository;
	
	@Autowired
    private MailRecordMapper mapper;
	
	@Autowired
    private MailSenderServiceImpl mailSenderService;
	
    Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);

	@Override
	public String sendMailAndSaveRecord(MailRequestDto request) {
		try {
			String email = request.getEmail();
	        String message = request.getMessage();

	        boolean isDuplicate = repository.existsByEmailAndMessageIgnoreCaseTrim(email, message);
	        if (isDuplicate) {
	            logger.warn("Duplicate mail detected for email: {} and message: {}", email, message);
	            return "Duplicate entry: same email and message already exist.";
	        }
	        MailRecord record = mapper.toEntity(request);
	        record.setSentAt(LocalDateTime.now());
	        record.setUpdatedAt(LocalDateTime.now());

	        repository.save(record);
	        logger.info("Mail record saved to DB for: {}", request.getEmail());
	        
	        mailSenderService.sendMail(request);
            logger.info("Mail sent successfully ");

            return "Mail sent and record saved successfully.";

	    } catch (Exception e) {
	        logger.error("Error sending mail or saving record: {}", e.getMessage());
	        throw new RuntimeException("Failed to send mail or save record: " + e.getMessage());
	    }
	}
	
	@Override
	public List<MailRecord> SaveRecordList(List<MailRecord> mails) {
		try {

			if (mails == null || mails.isEmpty()) {
	            logger.warn("No mail records provided to save or update.");
	            return Collections.emptyList();
	        }

	        List<MailRecord> existingRecords = repository.findAll();

	        Map<String, MailRecord> existingMap = new HashMap<>();
	        for (MailRecord existing : existingRecords) {
	            String key = (existing.getEmail().trim().toLowerCase() + "|" + existing.getMessage().trim().toLowerCase());
	            existingMap.put(key, existing);
	        }

	        Set<String> processedKeys = new HashSet<>();
	        List<MailRecord> recordsToSave = new ArrayList<>();

	        for (MailRecord incoming : mails) {
	            if (incoming.getEmail() == null || incoming.getName() == null) {
	                logger.warn("Skipping mail record with missing email or name.");
	                continue;
	            }

	            String key = (incoming.getEmail().trim().toLowerCase() + "|" + incoming.getMessage().trim().toLowerCase());

	            if (processedKeys.contains(key)) {
	            	 logger.warn("Duplicate in input skipped: Mail='{}', Message='{}'", incoming.getEmail(), incoming.getMessage());
	                 continue;
	            }

	            processedKeys.add(key);

	            if (existingMap.containsKey(key)) {
	                MailRecord existing = existingMap.get(key);

	                existing.setSubject(incoming.getSubject());
	                existing.setMessage(incoming.getMessage());
	                existing.setPhone(incoming.getPhone());
	                existing.setCompany(incoming.getCompany());
	                existing.setCountry(incoming.getCountry());
	                existing.setDestination(incoming.getDestination());
	                existing.setUpdatedAt(LocalDateTime.now());

	                recordsToSave.add(existing);
	            } else {
	                incoming.setSentAt(LocalDateTime.now());
	                incoming.setUpdatedAt(LocalDateTime.now());
	                recordsToSave.add(incoming);
	            }
	        }

	        if (recordsToSave.isEmpty()) {
	            logger.info("No new or updated mail records to save.");
	            return Collections.emptyList();
	        }

	        List<MailRecord> saved = repository.saveAll(recordsToSave);
	        logger.info("Saved {} mail records (created/updated)", saved.size());

	        return saved;

		} catch (Exception e) {
			logger.error("Error sending mail or saving record: {}", e.getMessage());
			throw new RuntimeException("Failed to send mail or save record: " + e.getMessage());
		}
	}

	@Override
	public List<MailRecord> getAllMails() {
		try {
	        logger.info("Fetching all mail records from the database.");

	        List<MailRecord> records = repository.findAll();

	        if (records.isEmpty()) {
	            logger.warn("No mail records found.");
	        } else {
	            logger.info("Total {} mail records fetched.", records.size());
	        }

	        return records;

	    } catch (Exception e) {
	        logger.error("Error occurred while fetching mail records: "+ e.getMessage());
	        throw new RuntimeException("Failed to fetch mail records: " + e.getMessage());
	    }
	}

	@Override
	public MailRecord getMailById(Long id) {
		try {
	        logger.info("Fetching mail record with ID: {}", id);

	        MailRecord record = repository.findById(id).orElse(null);
	        if (record == null) {
	            logger.warn("No mail record found with ID: {}", id);
	            throw new EntityNotFoundException("Mail record not found with ID: " + id);
	        }

	        logger.info("Mail record with ID {} fetched successfully.", id);
	        return record;

	    } catch (Exception e) {
	        logger.error("Error fetching mail record with ID {}:", id+ e.getMessage());
	        throw new RuntimeException("Error retrieving mail record: " + e.getMessage());
	    }
	}

	@Override
	public MailRecord updateMailRecord(Long id, MailRequestDto request) {
		try {
	        logger.info("Attempting to update mail record with ID: {}", id);

	        MailRecord existingRecord = repository.findById(id).orElse(null);
	        if (existingRecord == null) {
	            logger.warn("Mail record not found with ID: {}", id);
	            throw new EntityNotFoundException("Mail record not found with ID: " + id);
	        }
	        
	        existingRecord.setEmail(request.getEmail());
	        existingRecord.setName(request.getName());
	        existingRecord.setSubject(request.getSubject());
	        existingRecord.setMessage(request.getMessage());
	        existingRecord.setDestination(request.getDestination());
	        existingRecord.setCompany(request.getCompany());
	        existingRecord.setPhone(request.getPhone());
	        existingRecord.setCountry(request.getCountry());
	        existingRecord.setUpdatedAt(LocalDateTime.now());

	        MailRecord updatedRecord = repository.save(existingRecord);
	        logger.info("Mail record with ID {} updated successfully.", id);
	        return updatedRecord;

	    } catch (Exception e) {
	        logger.error("Error occurred while updating mail record with ID {}: {}", id, e.getMessage());
	        throw new RuntimeException("Error occurred while updating mail record: " + e.getMessage());
	    }
	}

	@Override
	public String deleteMailById(Long id) {
	    try {
	        if (!repository.existsById(id)) {
	            logger.warn("Mail record not found with ID: {}", id);
	            return "Mail record not found with ID: " + id;
	        }

	        repository.deleteById(id);
	        logger.info("Mail record with ID {} deleted successfully.", id);
	        return "Mail record deleted successfully!";
	    } catch (Exception e) {
	        logger.error("Deletion failed :"+ e.getMessage());
	        return "Error occured in deleting mail record by :"+e.getMessage();
	    }
	}

}
