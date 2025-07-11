package com.clavrit.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clavrit.Entity.Subscriber;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
	
	boolean existsByEmail(String email);

}
