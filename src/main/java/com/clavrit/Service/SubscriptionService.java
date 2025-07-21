package com.clavrit.Service;

import java.util.List;

import com.clavrit.Entity.Subscriber;

public interface SubscriptionService {
	
	String subscribe(String email);
	
	List<Subscriber> getAllSubscribers();
	List<Subscriber> saveAllSubscribers(List<Subscriber> subscribers);
}
