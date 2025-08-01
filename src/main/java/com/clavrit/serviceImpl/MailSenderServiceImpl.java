package com.clavrit.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.clavrit.Dto.MailRequestDto;

@Service
public class MailSenderServiceImpl {

	@Autowired
    private JavaMailSender mailSender;

    @Value("${contact.receiver.email}")
    private String toEmail;

    Logger logger = LoggerFactory.getLogger(MailSenderServiceImpl.class);


    public String sendMail(MailRequestDto request) {
        try {
            logger.info("Preparing to send email to: {}", toEmail);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(request.getEmail());
            message.setTo(toEmail);
            message.setSubject("Mail from: " + request.getName() + " | Subject: " + request.getSubject());
            message.setText(buildMessageFormat(request));

            mailSender.send(message);
            logger.info("Email sent successfully to: {}", toEmail);
            
            return "Mail sent successfully";

        } catch (Exception e) {
            logger.error("Failed to send mail: {}", e.getMessage());
            throw new RuntimeException("Failed to send mail: " + e.getMessage());
        }
    }
    
    private String buildMessageFormat(MailRequestDto request) {
        return String.format(
        		"You have received a new contact request!%n%n"
        				+ "      Name       : %s%n"
        		        + "      Email      : %s%n"
        		        + "      Phone      : %s%n"
        		        + "      Company    : %s%n"
        		        + "      Country    : %s%n"
        		        + "      Subject    : %s%n%n"
        		        + "      Message:%n"
        		        + "      ------------------------------------------------------------%n"
        		        + "      %s%n"
        		        + "      ------------------------------------------------------------%n%n"
        		        + "Destination: %s",
        		
                request.getName(),
                request.getEmail(),
                request.getPhone(),
                request.getCompany(),
                request.getCountry(),
                request.getSubject(),
                request.getMessage(),
                request.getDestination()
        );
    }
}
