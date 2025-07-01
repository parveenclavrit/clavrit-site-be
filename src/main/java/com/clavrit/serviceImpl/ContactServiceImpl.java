package com.clavrit.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.clavrit.Dto.MailRequestDto;
import com.clavrit.Entity.MailRecord;
import com.clavrit.Repository.MailRecordRepository;
import com.clavrit.Service.ContactService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ContactServiceImpl implements ContactService {
	
	@Autowired
	private MailRecordRepository repository;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${contact.receiver.email}")
    private String toEmail;
	
    Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);

	@Override
	public String sendMailAndSaveRecord(MailRequestDto request) {
		try {
	        logger.info("Preparing to get mail from: {}", request.getEmail());

	        SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(request.getEmail());
            message.setTo(toEmail);
	        message.setSubject("Mail from: " + request.getName() + " | Subject: " + request.getSubject());
	        message.setText(
	                "Email: " + request.getEmail() + "\n" +
	                "Name: " + request.getName() + "\n" +
	                "Subject: " + request.getSubject() + "\n" +
	                "Message: " + request.getMessage() + "\n" +
	                "Company: " + request.getCompany() + "\n" +
	                "Phone: " + request.getPhone() + "\n" +
	                "Country: " + request.getCountry()
	        );

	        mailSender.send(message);
	        logger.info("Mail sent successfully to: {}", request.getDestination());

	        MailRecord record = new MailRecord();
	        record.setEmail(request.getEmail());
	        record.setName(request.getName());
	        record.setSubject(request.getSubject());
	        record.setMessage(request.getMessage());
	        record.setDestination(request.getDestination());
	        record.setCompany(request.getCompany());
	        record.setPhone(request.getPhone());
	        record.setCountry(request.getCountry());
	        record.setSentAt(LocalDateTime.now());

	        repository.save(record);
	        logger.info("Mail record saved to DB for: {}", request.getEmail());

	        return "Mail sent and record saved successfully.";

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
