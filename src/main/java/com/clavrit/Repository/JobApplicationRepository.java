package com.clavrit.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clavrit.Entity.JobApplication;



public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
}
