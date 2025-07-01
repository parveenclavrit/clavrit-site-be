package com.clavrit.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.clavrit.Entity.ApiResponse;
import com.clavrit.Entity.MailRecord;
import com.clavrit.Service.ContactService;

@RestController
@RequestMapping("/clavrit/contact")
public class ContactController {
	
	@Autowired
    private ContactService contactService;

    Logger logger = LoggerFactory.getLogger(ContactController.class);
    
    @PostMapping("/send")
    public ApiResponse sendMail(@RequestBody MailRequestDto request) {
        try {
            String result = contactService.sendMailAndSaveRecord(request);
            return new ApiResponse(true, 201, "Mail sent successfully", result);
        } catch (Exception e) {
            return new ApiResponse(false, 500, "Failed to send mail", e.getMessage());
        }
    }

    @GetMapping
    public ApiResponse getAllMails() {
        try {
            List<MailRecord> mails = contactService.getAllMails();
            return new ApiResponse(true, 200, "Mail records fetched successfully", mails);
        } catch (Exception e) {
            return new ApiResponse(false, 500, "Failed to fetch mail records", e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ApiResponse getMailById(@PathVariable Long id) {
        try {
            MailRecord mail = contactService.getMailById(id);
            return new ApiResponse(true, 200, "Mail record fetched", mail);
        } catch (Exception e) {
            return new ApiResponse(false, 404, "Mail record not found", e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ApiResponse updateMail(@PathVariable Long id, @RequestBody MailRequestDto request) {
        try {
            MailRecord updated = contactService.updateMailRecord(id, request);
            return new ApiResponse(true, 200, "Mail record updated successfully", updated);
        } catch (Exception e) {
            return new ApiResponse(false, 500, "Failed to update mail record", e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteMail(@PathVariable Long id) {
        try {
            String result = contactService.deleteMailById(id);
            return new ApiResponse(true, 200, "Mail service deletion process done ", result);
        } catch (Exception e) {
            return new ApiResponse(false, 500, "Failed to delete mail record", e.getMessage());
        }
    }

}
