package com.clavrit.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clavrit.Dto.MailRequestDto;
import com.clavrit.Entity.MailRecord;
import com.clavrit.Enums.ApiStatus;
import com.clavrit.Service.ContactService;
import com.clavrit.response.ApisResponse;

@RestController
@RequestMapping("/clavrit/contact")
public class ContactController {
	
	@Autowired
    private ContactService contactService;
    
    @PostMapping("/send")
    public ApisResponse sendMail(@RequestBody MailRequestDto request) {
        try {
            String result = contactService.sendMailAndSaveRecord(request);
            return new ApisResponse(ApiStatus.CREATED, "Mail sent successfully", result);
        } catch (Exception e) {
        	return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Failed to send mail", e.getMessage());
        }
    }

    @GetMapping
    public ApisResponse getAllMails() {
        try {
            List<MailRecord> mails = contactService.getAllMails();
            return new ApisResponse(ApiStatus.OK, "Mail records fetched successfully", mails);
        } catch (Exception e) {
        	return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Failed to fetch mail records", e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ApisResponse getMailById(@PathVariable Long id) {
        try {
            MailRecord mail = contactService.getMailById(id);
            return new ApisResponse(ApiStatus.OK, "Mail record fetched", mail);
        } catch (Exception e) {
        	return new ApisResponse(ApiStatus.NOT_FOUND, "Mail record not found", e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ApisResponse updateMail(@PathVariable Long id, @RequestBody MailRequestDto request) {
        try {
            MailRecord updated = contactService.updateMailRecord(id, request);
            return new ApisResponse(ApiStatus.OK, "Mail record updated successfully", updated);
        } catch (Exception e) {
        	return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Failed to update mail record", e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ApisResponse deleteMail(@PathVariable Long id) {
        try {
            String result = contactService.deleteMailById(id);
            return new ApisResponse(ApiStatus.OK, "Mail record deletion process done", result);
        } catch (Exception e) {
        	return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Failed to delete mail record", e.getMessage());
        }
    }

}
