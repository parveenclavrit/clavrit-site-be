package com.clavrit.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.clavrit.Entity.Subscriber;
import com.clavrit.Repository.SubscriberRepository;
import com.clavrit.Service.SubscriptionService;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
	
	Logger log = LoggerFactory.getLogger(SubscriptionServiceImpl.class);
	
	@Autowired
    private SubscriberRepository subscriberRepository;
	
	@Autowired
    private JavaMailSender mailSender;

	@Override
	public String subscribe(String email) {
		try {
            if (subscriberRepository.existsByEmail(email)) {
                log.warn("Subscription attempt with already subscribed email: {}", email);
                return "This email is already subscribed!";
            }

            Subscriber subscriber = new Subscriber();
            subscriber.setEmail(email);
            subscriber.setSubscribedAt(LocalDateTime.now());

            subscriberRepository.save(subscriber);
            log.info("New subscriber added with email: {}", email);

            sendThankYouEmail(email);
            return "Subscribed successfully. Thank you!";
        } catch (Exception e) {
            log.error("Failed to subscribe email {}: {}", email, e.getMessage());
            throw new RuntimeException("Subscription failed: " + e.getMessage());
        }
	}

	@Override
	public List<Subscriber> getAllSubscribers() {
		try {
            List<Subscriber> subscribers = subscriberRepository.findAll();
            log.info("Fetched all subscribers, count: {}", subscribers.size());
            return subscribers;
        } catch (Exception e) {
            log.error("Error while fetching subscribers: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch subscribers: " + e.getMessage());
        }
	}
	
	private void sendThankYouEmail(String toEmail) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Thank you for subscribing!");
            message.setText(
            		"👋 Hello!\n\n" +
            	    "Thank you for subscribing to *Clavrit Technologies* 🎉\n\n" +
            	    "We're excited to have you on board! You'll now receive the latest updates,\n" +
            	    "insights, and exclusive content straight to your inbox.\n\n" +
            	    "🔔 Stay tuned — we promise to keep it valuable.\n\n" +
            	    "With gratitude,\n" +
            	    "— Team Clavrit 🚀"
            	);

            mailSender.send(message);
            log.info("Thank-you email sent to: {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", toEmail, e.getMessage());
        }
    }
	
	@Override
	public List<Subscriber> saveAllSubscribers(List<Subscriber> subscribers) {
	    try {
	        return subscriberRepository.saveAll(subscribers);
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to save subscribers: " + e.getMessage());
	    }
	}


}
