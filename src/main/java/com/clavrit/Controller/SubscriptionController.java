package com.clavrit.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clavrit.Entity.Subscriber;
import com.clavrit.Enums.ApiStatus;
import com.clavrit.Service.SubscriptionService;
import com.clavrit.response.ApisResponse;

@RestController
@RequestMapping("/clavrit/subscribe")
public class SubscriptionController {
	
	@Autowired
    private SubscriptionService subscriptionService;
	
	@PostMapping
    public ApisResponse subscribe(@RequestParam("email") String email) {
        try {
            if (email == null || email.trim().isEmpty()) {
                return new ApisResponse(ApiStatus.BAD_REQUEST, "Email is required", null);
            }

            String result = subscriptionService.subscribe(email);
            return new ApisResponse(ApiStatus.CREATED, result, null);

        } catch (Exception e) {
            return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Subscription failed", e.getMessage());
        }
    }

    @GetMapping
    public ApisResponse getAllSubscribers() {
        try {
            List<Subscriber> subscribers = subscriptionService.getAllSubscribers();
            return new ApisResponse(ApiStatus.OK, "Subscribers fetched successfully", subscribers);
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Error fetching subscribers", e.getMessage());
        }
    }

}
