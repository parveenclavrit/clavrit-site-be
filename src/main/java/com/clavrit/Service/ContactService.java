package com.clavrit.Service;

import java.util.List;

import com.clavrit.Dto.MailRequestDto;
import com.clavrit.Entity.MailRecord;

public interface ContactService {
	
	String sendMailAndSaveRecord(MailRequestDto request);

    List<MailRecord> getAllMails();

    MailRecord getMailById(Long id);

    MailRecord updateMailRecord(Long id, MailRequestDto request);

    String deleteMailById(Long id);

}
