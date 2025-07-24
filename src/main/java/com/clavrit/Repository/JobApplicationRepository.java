package com.clavrit.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clavrit.Entity.JobApplication;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
	
	boolean existsByEmailIgnoreCaseAndPhoneAndJobAppliedForIgnoreCase(String email, String phone, String jobAppliedFor);

}
