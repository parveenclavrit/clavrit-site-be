package com.clavrit.mapper;
import org.springframework.stereotype.Component;

import com.clavrit.Dto.MailRequestDto;
import com.clavrit.Entity.MailRecord;

@Component
public class MailRecordMapper {

    public MailRecord toEntity(MailRequestDto dto) {
        if (dto == null) return null;

        MailRecord mail = new MailRecord();
        mail.setEmail(dto.getEmail());
        mail.setName(dto.getName());
        mail.setSubject(dto.getSubject());
        mail.setMessage(dto.getMessage());
        mail.setDestination(dto.getDestination());
        mail.setCompany(dto.getCompany());
        mail.setPhone(dto.getPhone());
        mail.setCountry(dto.getCountry());

        return mail;
    }
}
